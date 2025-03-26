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
import { Observable } from 'rxjs';
import { EquipmentPage } from '../../../model/equipment/equipment-page';
import { MatTableDataSource } from '@angular/material/table';
import { Department } from '../../../model/department/department';
import { UserDepartment } from '../../../model/department/user-department/user-department';
import { ShiftType } from '../../../model/ShiftType';
import { IconMapping } from '../../../model/filter/IconMapping';
import { DateFilterOperation, FilterConfig, FilterOperation } from '../../../model/filter/FilterConfig';
import { InputField } from '../../../model/filter/InputField';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FilterState } from '../../../model/filter/FilterState';
import { SelectionModel } from '@angular/cdk/collections';
import { MatCheckboxChange } from '@angular/material/checkbox';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  allEquipments: Equipment[] = []; 
  filteredEquipments: Equipment[] = [];
  dataSource = new MatTableDataSource<Equipment>([])
  activeFilters: Map<string, FilterState> = new Map();
  isFiltering: boolean = false; //track if we are filtering some propertie from table equipments or not

  idEquipments: number[] = []

  pageIndex = 0;
  pageSize = 10;
  totalElements = 0;

  isAvailableToProceedToUserCreation: boolean = false;

  selection = new SelectionModel<Equipment>(true, [])

  equipmentSelectedId: number = 0;
  formatedDate: string = ''
  
  canShowClearFilterForTypeEquipmentCategory: boolean = false;

  currentFilterValue: string = ''; //keyword that we are filtering 
  currentFilterOperation: string = ''; //description, type, sn, registryDate

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
    },
    'registryDate': {
      'Equals': {
        icon: 'equal',
        operation: 'Equals',
        filterFn: (value: string) => this.adminService.filterEquipmentsEqualsRegistryDate(value)
      },
      'Smaller than': {
        icon: 'equal',
        operation: 'Equals',
        filterFn: (value: string) => this.adminService.filterEquipmentsEqualsRegistryDate(value)
      },
      'Greater than': {
        icon: 'equal',
        operation: 'Equals',
        filterFn: (value: string) => this.adminService.filterEquipmentsEqualsRegistryDate(value)
      },
      'Smaller than or equal': {
        icon: 'equal',
        operation: 'Equals',
        filterFn: (value: string) => this.adminService.filterEquipmentsEqualsRegistryDate(value)
      }
    },
  };

  displayedColumns: string[] = ['select', 'description', 'serialNumber', 'type', 'registryDate'];

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private datePipe: DatePipe, private snackBar: MatSnackBar, private location: Location
  ){}

  ngOnInit(): void {
    this.refreshEquipments();
    this.refreshDepartments();    
  }

  private getInputValue(input: ElementRef | MatSelect): string {
    if (input instanceof ElementRef){
      return input.nativeElement.value;
    }else{
      return input.value
    }
  }

  getEquipmentsSelected(select: MatCheckboxChange, row?: Equipment){
    if (row){
      if (select.checked){
        this.idEquipments.push(row.id);
        console.log(row)
        console.log(this.idEquipments.length)
      }else{
        const index = this.idEquipments.indexOf(row.id);
        if (index > -1){
          this.idEquipments.splice(index, 1)
        }
      }
    }  
  }

  private setInputValue(input: ElementRef | MatSelect, value: string): void {
    if (input instanceof ElementRef){
      input.nativeElement.value = value;
    }else{
      input.value = value;
    }
  }

  menuItemClickedDate(item: DateFilterOperation, fieldName: string):void{
    switch(fieldName){
      case 'description':
        this.filterLogoSymbolChoosedForDescriptionInput = this.iconMap[item];
        if (item === 'All'){
          console.log("hello")
          this.setInputValue(this.descriptionInput, '')
        }
        break;
      case 'serialNumber':
        this.filterLogoSymbolChoosedForSerialNumberInput = this.iconMap[item];
        if (item === 'All'){
          this.setInputValue(this.serialNumberInput, '')
        }
        break;  
      case 'type':
        this.filterLogoSymbolChoosedForTypeInput = this.iconMap[item];
        if (item === 'All'){
          this.setInputValue(this.equipmentTypeSelect, '')
        }
        break;   
      case 'registryDate':
        this.filterLogoSymbolChoosedForRegistryDateInput = this.dateIconMap[item];
        break;
    }

    if (item === 'All'){
      if (this.currentFilterOperation === fieldName){
        this.resetFilters();
      }
    }else{
      const input = this.inputFields[fieldName].input;
      if (input){
        const value = this.getInputValue(input);
        if (value){
          this.applyFilter({ target: { value } } as any, fieldName)
        }
      }
    }
  }

  isAllSelected(){
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  toogleAllRows(){
    if (this.isAllSelected()){
      this.selection.clear();
      this.idEquipments = []
      return;
    }

    this.selection.select(...this.dataSource.data)
    this.idEquipments = this.dataSource.data.map(equipment => equipment.id)
  }

  checkboxLabel(row?: Equipment): string {
    if (!row){
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }

    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }

  clearSelectTypeEquipmentFilter(){
    if (this.equipmentTypeSelect){
      this.equipmentTypeSelect.value = ''
      this.selectedEquipmentType = '';
      this.activeFilters.delete('type')
      this.canShowClearFilterForTypeEquipmentCategory = false;
      this.executeFilters(); 
    }
  }

  resetFilters():void {
    this.isFiltering = false
    this.pageIndex = 0;
    this.pageSize = 10;
    this.activeFilters.clear();
    this.selectedEquipmentType = '';
    this.canShowClearFilterForTypeEquipmentCategory = false;

    if (this.descriptionInput) this.descriptionInput.nativeElement.value = '';
    if (this.serialNumberInput) this.serialNumberInput.nativeElement.value = '';
    if (this.equipmentTypeSelect) this.equipmentTypeSelect.value= '';

    this.filterLogoSymbolChoosedForDescriptionInput = 'filter_alt';
    this.filterLogoSymbolChoosedForSerialNumberInput = 'filter_alt';
    this.filterLogoSymbolChoosedForTypeInput = 'filter_alt';

    this.refreshEquipments();
  }

  updateDataSource(data: Equipment[]):void{
    this.filteredEquipments = data;
    this.applyPagination();
  }

  private applyPagination():void {
    const data = this.isFiltering ? this.filteredEquipments : this.allEquipments
    const startIndex = this.pageIndex * this.pageSize;
    const endIndex = Math.min(startIndex + this.pageSize, data.length);
  
    this.dataSource.data = data.slice(startIndex, endIndex);
    this.totalElements = data.length
  }

  refreshDepartments(){
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    })
  }

  refreshEquipments(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
    this.adminService.getEquipments(pageEvent.pageIndex, pageEvent.pageSize).subscribe((res) => {
      this.allEquipments = res.content.filter(e => e.statusEquipment === StatusEquipment.AVAILABLE);
      this.totalElements = res.totalElements;
      this.pageSize = pageEvent.pageSize
      this.pageIndex = pageEvent.pageIndex

      if (this.activeFilters.size > 0){
        this.executeFilters();
      } else {
        this.dataSource.data = this.allEquipments
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
      this.adminService.createUserWithEquipments({ user: employeeRegisterFormData, equipmentIds: this.idEquipments }).subscribe(
        (res) => {
          this.onSucess();
          },
          (error) => {
          this.onError();
          }
        );
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
      this.applyPagination();
    }else{
      this.refreshEquipments(event);
    }
    return event;
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
          } 
          break;
      case 'serialNumber':
          this.filterLogoSymbolChoosedForSerialNumberInput = iconMap[item];
          if (item === 'All'){
            this.serialNumberInput.nativeElement.value = ''
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
    const value = (event.target as HTMLInputElement).value;
    this.currentFilterValue = value;
    this.currentFilterOperation = filterProperty;
    
    if (value?.trim()){
      const field = this.inputFields[filterProperty];
      const currentIcon = (this as any)[field.iconKey];
      const operation = Object.entries(this.filterConfigs[field.filterProperty])
        .find(([__dirname,config]) => config.icon === currentIcon)?.[0] || 'Contains';

        this.activeFilters.set(filterProperty, {
          value: value.trim(),
          operation,
          field: filterProperty
        })
      
      this.filterConfigs[field.filterProperty][operation].filterFn(value.trim())
        .subscribe(res => {
          this.allEquipments = res;
          this.executeFilters();
        });
    }else{
      this.activeFilters.delete(filterProperty);
      if (this.activeFilters.size === 0){
        this.isFiltering = false;
        this.refreshEquipments();
      }else{
        this.applyExistingFilters();
      }
    }
  }

  applyDateFilter(event: MatDatepickerInputEvent<any>, field: string) {
    let selectedDate: Date = event.value;
    this.formatedDate = selectedDate.toLocaleDateString("pt-PT")
    this.formatedDate = this.formatedDate.replaceAll("/", "-")
  }

  applyFilterToEquipments(){
    this.isFiltering = true
    this.pageIndex = 0

    
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

  getEquipmentTypeSelected(event: MatSelectChange){
    if (event.value){
      this.selectedEquipmentType = event.value;

      this.activeFilters.set('type', {
        value: event.value,
        operation: 'Equals',
        field: 'type'
      });
      this.canShowClearFilterForTypeEquipmentCategory = true;

      this.adminService.filterEquipmentsByType(event.value)
        .subscribe(res => {
          this.isFiltering = true;
          this.allEquipments = res;
          this.updateFilteredResults(res)
      });
    }else{
      this.selectedEquipmentType = ''
      this.activeFilters.delete('type')
      this.canShowClearFilterForTypeEquipmentCategory = false;
      this.refreshEquipments();
    }
  }

  private applyExistingFilters(){
    if (this.activeFilters.size === 0){
      this.isFiltering = false;
      this.refreshEquipments();
      return;
    }

    const [firstKey, firstFilter] = Array.from(this.activeFilters.entries())[0];
    const operation = Object.entries(this.filterConfigs[firstKey])
      .find(([_,config]) => config.icon === (this as any)[this.inputFields[firstKey].iconKey])?.[0];
    
    if (operation){
      this.filterConfigs[firstKey][operation].filterFn(firstFilter.value)
        .subscribe(res => {
          let results = res;
          this.activeFilters.forEach((filter, key) => {
            if (key !== firstKey){
              results = this.applyFilterLocally(results, filter);
            }
          });
          this.updateFilteredResults(results)
        })
    }
  }

  private applyFilterLocally(data: Equipment[], filter: FilterState): Equipment[] {
    switch (filter.field){
      case 'type':
        return data.filter(item => 
            item.type.toLowerCase() === filter.value.toLowerCase());
      case 'description':
        return data.filter(item => {
          const description = item.description.toLowerCase();
          const searchValue = filter.value.toLowerCase();
          return filter.operation === 'Contains'
            ? description.includes(searchValue)
            : description.startsWith(searchValue)
        });
      case 'serialNumber':
        return data.filter(item => {
            const serialNumber = item.serialNumber.toLowerCase();
            const searchValue = filter.value.toLowerCase();

            return filter.operation === 'Contains' 
                ? serialNumber.includes(searchValue)
                : serialNumber.startsWith(searchValue);
        });
    default:
        return data;
    }
  }

  private executeFilters(){
    if (this.activeFilters.size === 0){
      this.isFiltering = false;
      this.refreshEquipments();
      return;
    }

    this.isFiltering = true;
    let results = [...this.allEquipments];

    this.activeFilters.forEach((filter) => {
      switch(filter.field){
        case 'type':
          results = results.filter(item => 
            item.type.toLowerCase() === filter.value.toLowerCase()
          );
          break;
        case 'description':
          results = results.filter(item => {
            const description = item.description.toLowerCase();
            const value = filter.value.toLowerCase();
            return filter.operation === 'Contains'
              ? description.includes(value)
              : description.startsWith(value)
          });
          break;
        case 'serialNumber':
          results = results.filter(item => {
              const serialNumber = item.serialNumber.toLowerCase();
              const value = filter.value.toLowerCase();
              return filter.operation === 'Contains'
                  ? serialNumber.includes(value)
                  : serialNumber.startsWith(value);
          });
          break;   
      }
    });
    this.updateFilteredResults(results)
  }
  
  private updateFilteredResults(results: Equipment[]) {
    this.filteredEquipments = results;
    this.totalElements = results.length;
    this.pageIndex = 0;
    this.applyPagination();
  }

  setActiveFilter(fieldName: string){
    this.activeFilterField = fieldName;
  }

  updatePaginator(){
    if (this.isFiltering){
      this.applyPagination();
      this.totalElements = this.filteredEquipments.length;
    }
  }
}
