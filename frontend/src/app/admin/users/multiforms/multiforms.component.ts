import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { UserRole } from '../../../model/user/userRole';
import { DatePipe, Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSelect, MatSelectChange } from '@angular/material/select';
import { Equipment } from '../../../model/equipment/equiment';
import { StatusEquipment } from '../../../model/equipment/status-equipment';
import { PageEvent } from '@angular/material/paginator';
import { config, Observable } from 'rxjs';
import { EquipmentPage } from '../../../model/equipment/equipment-page';
import { MatTableDataSource } from '@angular/material/table';
import { Department } from '../../../model/department/department';
import { UserDepartment } from '../../../model/department/user-department/user-department';
import { ShiftType } from '../../../model/ShiftType';
import { IconMapping } from '../../../model/filter/IconMapping';
import { DateFilterOperation, FilterConfig, FilterOperation } from '../../../model/filter/FilterConfig';
import { InputField } from '../../../model/filter/InputField';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  allEquipments: Equipment[] = []; 
  filteredEquipments: Equipment[] = [];

  equipmentSelectedId: number = 0;

  isFiltering: boolean = false; //track if we are filtering some propertie from table equipments or not
  canShowClearFilterForTypeEquipmentCategory: boolean = false;

  currentFilterValue: string = ''; //keyword that we are filtering 
  currentFilterOperation: string = ''; //description, type, sn, registryDate

  dataSource: MatTableDataSource<Equipment> = new MatTableDataSource<Equipment>([])

  departments: Department[] = [];
  departmentSelectedId: number = 0;

  userDepartment!: UserDepartment;

  equipmentsCategoryType: string[] = [ 'Desktop', 'Dock', 'Keyboard', 'Display', 'Notebook', 'Caliper', 'Pen Wifi', 'Printer', 'Scanner', 'Support Notebook', 
    'Support Scanner', 'Cell Phone'
  ];

  equipmentsAvailable$: Observable<EquipmentPage> | null = null;

  @ViewChild('descriptionInput') descriptionInput!: ElementRef;
  @ViewChild('serialNumberInput') serialNumberInput!: ElementRef;
  @ViewChild('equipmentTypeSelect') equipmentTypeSelect!: MatSelect;
  @ViewChild('registryDateInput') registryDateInput!: ElementRef;

  selectedEquipmentType: string = '';

  pageIndex = 0;
  pageSize = 10;
  totalElements = 0;

  //TEMPORARILY. Further we need to catch from the API
  UserRole = UserRole;

  typeAssignmentSelected: string = '';
  selectedValueCreationAssignmentType: any

  //TEMPORARILY. Further we need to catch from the API
  shiftTypes: ShiftType[] = [
    {value: 0, viewValue: 'MORNING'}, 
    {value: 1, viewValue: 'AFTERNOON'},
    {value: 2, viewValue: 'NIGHT'},
  ];

  isLinear = false;

  activeFilterField: string = ''

  filterLogoSymbolChoosedForDescriptionInput: string = 'filter_alt'; //Default icon if no menu option selected
  filterLogoSymbolChoosedForSerialNumberInput: string = 'filter_alt';
  filterLogoSymbolChoosedForTypeInput: string = 'filter_alt';
  filterLogoSymbolChoosedForRegistryDateInput: string = 'filter_alt';

  private readonly iconMap: IconMapping = {
    'All': 'filter_alt',
    'Contains': 'asterisk',
    'Starts With': 'flag',
    'Equals': 'equal'
  };

  private readonly dateIconMap: Record<DateFilterOperation, string> = {
    'All': 'filter_alt',
    'Greater Than': 'chevron_right',
    'Less Than': 'chevron_left',
    'Greater than or Equal': 'greater_than_or_equal',
    'Less than or Equal': 'less_than_or_equal',
    'Equals': 'equal'
  }

  private readonly inputFields: { [key: string]: InputField } = {
    'description': {
      input: this.descriptionInput,
      iconKey: 'filterLogoSymbolChoosedForDescriptionInput',
      filterProperty: 'description'
    },
    'serialNumber': {
      input: this.serialNumberInput,
      iconKey: 'filterLogoSymbolChoosedForSerialNumberInput',
      filterProperty: 'serialNumber'
    },
    'type': {
      input: this.equipmentTypeSelect,
      iconKey: 'filterLogoSymbolChoosedForTypeInput',
      filterProperty: 'type'
    },
    'registryDate': {
      input: this.registryDateInput,
      iconKey: 'filterLogoSymbolChoosedForRegistryDateInput',
      filterProperty: 'registryDate'
    }
  };

  private readonly filterConfigs: { [key: string]:  { [operation: string]: FilterConfig } } = {
    'description': {
      'Contains': {
        icon: 'asterisk',
        operation: 'Contains',
        filterFn: (value: string) => this.adminService.filterEquipmentsContainsDescription(value)
      },
      'Starts With': {
        icon: 'flag',
        operation: 'Starts With',
        filterFn: (value: string) => this.adminService.filterEquipmentsStartsWithDescription(value)
      }
    },
    'serialNumber': {
      'Contains': {
        icon: 'asterisk',
        operation: 'Contains',
        filterFn: (value: string) => this.adminService.filterEquipmentsContainsSerialNumber(value)
      },
      'Starts With': {
        icon: 'flag',
        operation: 'Starts With',
        filterFn: (value: string) => this.adminService.filterEquipmentsStartsWithSerialNumber(value)
      }
    }
     
  };

  displayedColumns: string[] = ['description', 'serialNumber', 'type', 'registryDate'];

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private datePipe: DatePipe, private snackBar: MatSnackBar, private location: Location
  ){}

  ngOnInit(): void {
    this.refreshEquipments();
    this.refreshDepartments();    
  }

  menuItemClickedDate(item: DateFilterOperation, fieldName: string):void{
    switch(fieldName){
      case 'registryDate':
        this.filterLogoSymbolChoosedForRegistryDateInput = this.dateIconMap[item];
        break;
    }

    if (item === 'All'){
      if (this.currentFilterOperation === fieldName){
        this.resetFilters();
        this.registryDateInput.nativeElement.value = '';
        this.refreshEquipments();
      }
    }
  }

  clearSelectTypeEquipmentFilter(){
    this.equipmentTypeSelect.value = ''
  }

  resetFilters():void {
    this.currentFilterValue = ''
    this.currentFilterOperation = ''
    this.isFiltering = false
    this.pageIndex = 0;
    this.pageSize = 10;

    this.refreshEquipments();
  }

  updateDataSource(data: Equipment[]):void{
    this.filteredEquipments = data;
    this.updatePaginator();
  }

  refreshDepartments(){
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    })
  }

  refreshEquipments(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
      this.adminService.getEquipments(pageEvent.pageIndex, pageEvent.pageSize).subscribe((res) => {
        this.dataSource.data = res.content.filter(e => e.statusEquipment === StatusEquipment.AVAILABLE)
        
        this.totalElements = res.totalElements;
        this.pageSize = pageEvent.pageSize;
        this.pageIndex = pageEvent.pageIndex;

        if (this.isFiltering){
          this.allEquipments = [...this.dataSource.data];
          this.filteredEquipments = [...this.dataSource.data]
        }
      });
  }

  employeeRegister = this.formBuilder.group({
    basic: this.formBuilder.group({
      firstName: this.formBuilder.control('', Validators.required),
      lastName: this.formBuilder.control('', Validators.required),
      birthdayDate: this.formBuilder.control(''),
      nif: this.formBuilder.control('', Validators.required),
      registryDate: this.datePipe.transform(new Date(), 'dd/MM/yyyy HH:mm'),

    }),

    contact: this.formBuilder.group({
      email: this.formBuilder.control('', Validators.required),
      contactNumber: this.formBuilder.control('', Validators.required),
    }),

    professionalData: this.formBuilder.group({
      workNumber: this.formBuilder.control('', Validators.required),
      department: this.formBuilder.control(null, Validators.required),
      admissionDate: this.formBuilder.control('', Validators.required),
      recruitmentCompany: this.formBuilder.control('', Validators.required),
      shiftType: this.formBuilder.control('', Validators.required),
    }),
    
    security: this.formBuilder.group({
      userRole: [UserRole.USER],
      password: this.formBuilder.control('', Validators.required)
    })
  })

  onSubmit(){
    const employeeRegisterFormData = {
      firstName: this.employeeRegister.value.basic?.firstName,
      lastName: this.employeeRegister.value.basic?.lastName,
      birthdayDate: this.employeeRegister.value.basic?.birthdayDate,
      email: this.employeeRegister.value.contact?.email,
      contactNumber: this.employeeRegister.value.contact?.contactNumber,
      registryDate: this.employeeRegister.value.basic?.registryDate,
      nif: this.employeeRegister.value.basic?.nif,
      workNumber: this.employeeRegister.value.professionalData?.workNumber,
      department: this.employeeRegister.value.professionalData?.department,
      admissionDate: this.datePipe.transform(this.employeeRegister.value.professionalData?.admissionDate, 'dd/MM/yyyy'),
      recruitmentCompany: this.employeeRegister.value.professionalData?.recruitmentCompany,
      shiftType: this.employeeRegister.value.professionalData?.shiftType,
      userRole: this.employeeRegister.value.security?.userRole,
      password: this.employeeRegister.value.security?.password
    };

    if (this.typeAssignmentSelected === 'EQU'){
      this.adminService.createUserAndAssignToEquipment(employeeRegisterFormData, this.equipmentSelectedId).subscribe(
        (res) => {
          this.onSucess();
        },
        (error) => {
          this.onError();
        })
    }else if (this.typeAssignmentSelected === 'DEP'){
      this.adminService.createUserAndAssignToDepartment(employeeRegisterFormData, this.departmentSelectedId).subscribe((res) => {
        this.userDepartment = res;
        if (this.userDepartment.id != null){
          this.snackBar.open("USER WITH ID " + res.user.id + " CREATED AND ASSIGNED TO DEPARTMENT " + res.department.description, "X")
        }
      })
    }else if (this.typeAssignmentSelected === 'LOC'){
      console.log('LOC TYPE ASSINGMENT....')
    }else{
      this.adminService.createUser(employeeRegisterFormData).subscribe(
        (res) => {
          this.onSucess();
        },
        (error) => {
          this.onError();
        })
    }
  }

  onCancel(){
    this.location.back();
  }

  private onSucess(){
    this.snackBar.open('User created sucessfully [ ' + this.employeeRegister.value.professionalData?.workNumber + " ] " + this.employeeRegister.value.basic?.firstName + " " + this.employeeRegister.value.basic?.lastName, 'X', { duration: 2000})
    this.onCancel();
  }

  private onError(){
    this.snackBar.open('Error saving user', 'X', { duration: 2000})
  }

  valueChoosedChanged(event: MatSelectChange){
      this.selectedValueCreationAssignmentType = {
        value: event.value,
        text: event.source.triggerValue
      };

      this.selectedValueCreationAssignmentType = this.selectedValueCreationAssignmentType.text;
  }

  onPageChange(event: PageEvent){
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    
    if (this.isFiltering){
      this.updatePaginator();
    }else{
      this.refreshEquipments(event);
    }
    
  }

  menuItemClicked(item: FilterOperation, fieldName: string):void{
    const iconMap: Record<FilterOperation, string> = {
      'All': 'filter_alt',
      'Contains': 'asterisk',
      'Starts With': 'flag',
      'Equals': 'equal'
    };

    switch(fieldName) {
      case 'description':
          this.filterLogoSymbolChoosedForDescriptionInput = iconMap[item];
          if (item === 'All'){
            this.descriptionInput.nativeElement.value = ''
          }else if (item === 'Contains'){
            this.adminService.filterEquipmentsContainsDescription(this.descriptionInput.nativeElement.value).subscribe((res) => {
              this.updateDataSource(res)
            });
            this.updatePaginator();
          }else if (item === 'Starts With'){
            this.adminService.filterEquipmentsStartsWithDescription(this.descriptionInput.nativeElement.value).subscribe((res) => {
              this.updateDataSource(res)
            })
            this.updatePaginator();
          } 
          break;
      case 'serialNumber':
          this.filterLogoSymbolChoosedForSerialNumberInput = iconMap[item];
          if (item === 'All'){
            this.serialNumberInput.nativeElement.value = ''
          }else if (item === 'Starts With'){
            this.adminService.filterEquipmentsStartsWithSerialNumber(this.serialNumberInput.nativeElement.value).subscribe((res) => {
              this.updateDataSource(res)
            })
            this.updatePaginator();
          }else if (item === 'Contains'){
            this.adminService.filterEquipmentsContainsSerialNumber(this.serialNumberInput.nativeElement.value).subscribe((res) => {
              this.updateDataSource(res)
            })
            this.updatePaginator();
          }
          break;
      case 'type':
          this.filterLogoSymbolChoosedForTypeInput = iconMap[item];
          if (item === 'All'){
            this.equipmentTypeSelect.value = ''
          }
          break;
      case 'registryDate':
          this.filterLogoSymbolChoosedForRegistryDateInput = iconMap[item];
          break;
    }

    if (item === 'All') {
      if (this.currentFilterOperation === fieldName) {
        this.resetFilters();
    }
  }}

  applyFilter(event: Event, filterProperty: string){
    this.currentFilterValue = (event.target as HTMLInputElement).value;
    this.currentFilterOperation = filterProperty;
    this.pageIndex = 0;
    this.applyFilterToEquipments();
  }

  applyDateFilter(event: MatDatepickerInputEvent<any>, field: string) {
    this.applyFilter(event.value, field);
  }

  applyFilterToEquipments(){
    this.isFiltering = true
    this.pageIndex = 0

    if (this.currentFilterOperation === 'registryDate'){
      const dateValue = new Date(this.currentFilterValue)
      const filterIcon = this.filterLogoSymbolChoosedForRegistryDateInput;

      this.filteredEquipments = this.allEquipments.filter(equipment => {
        const equipmentDate = new Date(equipment.registryDate)

      switch(filterIcon){
        case this.dateIconMap['Greater Than']:
          return equipmentDate > dateValue;
        case this.dateIconMap['Less Than']:
          return equipmentDate < dateValue;
        case this.dateIconMap['Greater than or Equal']:
          return equipmentDate >= dateValue    
        case this.dateIconMap['Less than or Equal']:
          return equipmentDate <= dateValue;
        case this.dateIconMap['Equals']:
          return equipmentDate.getTime() === dateValue.getTime();
        default:
          return true;      
      }
    });
      this.updateDataSource(this.filteredEquipments)
    }else{
      const field = this.inputFields[this.currentFilterOperation];
      const currentIcon = (this as any)[field.iconKey];

      const operation = Object.entries(this.filterConfigs[field.filterProperty])
        .find(([_, config]) => config.icon === currentIcon)?.[0];

      if (operation && this.currentFilterValue){
        this.filterConfigs[field.filterProperty][operation].filterFn(this.currentFilterValue)
          .subscribe(res => {
            this.updateDataSource(res);
          })
      }  
    }  
  }

  getEquipmentTypeSelected(event: MatSelectChange){
    this.selectedEquipmentType = event.value;

    if (this.selectedEquipmentType){
      this.canShowClearFilterForTypeEquipmentCategory = true;
    }
  }

  setActiveFilter(fieldName: string){
    this.activeFilterField = fieldName;
  }

  updatePaginator(){
      if (this.isFiltering){
        const startIndex = this.pageIndex * this.pageSize;
        const endIndex = Math.min(startIndex + this.pageSize, this.filteredEquipments.length);
        this.dataSource.data = this.filteredEquipments.slice(startIndex, endIndex)
      }
  }
}
