import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ShiftType } from '../../../model/ShiftType';
import { AdminService } from '../../services/admin.service';
import { UserRole } from '../../../model/user/userRole';
import { DatePipe, Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSelectChange } from '@angular/material/select';
import { Equipment } from '../../../model/equipment/equiment';
import { Department } from '../../../model/department/department';
import { UserDepartment } from '../../../model/department/user-department/user-department';
import { StatusEquipment } from '../../../model/equipment/status-equipment';
import { PageEvent } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { EquipmentPage } from '../../../model/equipment/equipment-page';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  allEquipments: Equipment[] = []; 
  equipmentsAvailable: Equipment[] = [];
  equipmentSelectedId: number = 0;
  filteredEquipments: Equipment[] = [];

  isFiltering: boolean = false; 

  currentFilterValue: string = '';
  currentFilterOperation: string = '';

  dataSource = new MatTableDataSource<Equipment>(this.filteredEquipments);

  departments: Department[] = [];
  departmentSelectedId: number = 0;

  userDepartment!: UserDepartment;

  equipmentsAvailable$: Observable<EquipmentPage> | null = null;

  @ViewChild('descriptionInput') descriptionInput!: ElementRef;

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


  filterLogoSymbolChoosed: string = 'filter_alt'; //Default icon if no menu option selected

  displayedColumns: string[] = ['description', 'serialNumber', 'type', 'registryDate'];

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private datePipe: DatePipe, private snackBar: MatSnackBar, private location: Location
  ){}

  ngOnInit(): void {
    this.refreshEquipments();
    this.refreshDepartments();    
  }

  refreshDepartments(){
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    })
  }

  refreshEquipments(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
    
    if (!this.isFiltering){
      this.adminService.getEquipments(pageEvent.pageIndex, pageEvent.pageSize).subscribe((res) => {
        this.equipmentsAvailable = res.content.filter(e => e.statusEquipment === StatusEquipment.AVAILABLE);
        this.allEquipments = this.equipmentsAvailable;
        this.totalElements = res.totalElements;
        this.pageSize = pageEvent.pageSize;
        this.pageIndex = pageEvent.pageIndex;
        this.dataSource.data = this.equipmentsAvailable
    });
    }else{
      this.updatePaginator();
    }
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
    return event;
  }

  menuItemClicked(item: string){
    if (item == 'All'){
      this.filterLogoSymbolChoosed = 'filter_alt';
      //clear the value search and the propertie value
      this.currentFilterValue = ''
      this.currentFilterOperation = '' 
      this.isFiltering = false;

      //Reset pagination
      this.pageIndex = 0;
      this.pageSize = 10;

      this.filteredEquipments = [...this.allEquipments];
      this.totalElements = this.allEquipments.length;

      this.refreshEquipments();
      this.clearInput();

    }else if (item == 'Contains'){
      this.filterLogoSymbolChoosed = 'asterisk'
    }else if (item == 'Starts With'){
      this.filterLogoSymbolChoosed = 'flag'
    }else if (item == 'Equals'){
      this.filterLogoSymbolChoosed = 'equal'
    }
  }

  clearInput(){
    this.descriptionInput.nativeElement.value = '';
  }

  applyFilter(event: Event, filterPropertieOperation: string){
    this.currentFilterValue = (event.target as HTMLInputElement).value;
    this.currentFilterOperation = filterPropertieOperation;
    this.pageIndex = 0;
    this.applyFilterToEquipments();
  }

  applyFilterToEquipments(){
    this.isFiltering = true;

    if (this.currentFilterOperation === 'description'){
      this.adminService.filterEquipmentsStartsWithDescription(this.currentFilterValue).subscribe((res) => {
        this.filteredEquipments = res;
        this.totalElements = this.filteredEquipments.length;
        this.updatePaginator();
      });
    }else{
      this.filteredEquipments = this.equipmentsAvailable.filter(equipment => {
        return (equipment[this.currentFilterOperation as keyof Equipment] as unknown as string)
        .toLowerCase()
        .includes(this.currentFilterValue.toLowerCase());
      });
      this.totalElements = this.filteredEquipments.length;
      this.pageIndex = 0;
      this.updatePaginator();
    }
  }

  updatePaginator(){
    const startIndex = this.pageIndex * this.pageSize;
    const endIndex = startIndex + this.pageSize;

    if (this.isFiltering){
      this.dataSource.data = this.filteredEquipments.slice(startIndex, endIndex)
    }else{
      this.dataSource.data = this.allEquipments.slice(startIndex, endIndex)
    }
  }
}
