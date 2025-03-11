import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  equipmentsAvailable: Equipment[] = [];
  equipmentSelectedId: number = 0;

  departments: Department[] = [];
  departmentSelectedId: number = 0;

  userDepartment!: UserDepartment;

  equipmentsAvailable$: Observable<EquipmentPage> | null = null;

  pageIndex = 0;
  pageSize = 10;
  totalElements = 0;

  displayedColumns: string[] = ['description', 'serialNumber', 'type', 'registryDate'];

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private datePipe: DatePipe, private snackBar: MatSnackBar, private location: Location
  ){}

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
    this.adminService.getEquipments(pageEvent.pageIndex, pageEvent.pageSize).subscribe((res) => {
      this.equipmentsAvailable = res.content.filter(e => e.statusEquipment === StatusEquipment.AVAILABLE);
      this.totalElements = res.totalElements;
      this.pageSize = pageEvent.pageSize;
      this.pageIndex = pageEvent.pageIndex;
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
    this.refreshEquipments(event);
  }

  menuItemClicked(item: string){
    console.log('Item clicked: ' + item);
  }
}
