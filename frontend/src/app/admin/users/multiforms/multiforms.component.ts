import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Department } from '../../../model/department';
import { ShiftType } from '../../../model/ShiftType';
import { AdminService } from '../../services/admin.service';
import { UserRole } from '../../../model/userRole';
import { DatePipe, formatDate } from '@angular/common';
import { User } from '../../../model/user';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private datePipe: DatePipe
  ){}

  //TEMPORARILY. Further we need to catch from the API
  UserRole = UserRole;

  //TEMPORARILY. Further we need to catch from the API
  departments: Department[] = [
    {value: 0, viewValue: "ADMINISTRATION"},
    {value: 1, viewValue: 'LOGISTIC'},
    {value: 2, viewValue: 'PURCHASING'},
    {value: 3, viewValue: 'HUMAN RESOURCES'},
    {value: 4, viewValue: 'INFORMATION TECHNOLOGY'},
    {value: 5, viewValue: 'PRODUCTION'},
    {value: 6, viewValue: 'DEVELOPMENT'},

  ];

  //TEMPORARILY. Further we need to catch from the API
  shiftTypes: ShiftType[] = [
    {value: 0, viewValue: 'MORNING'}, 
    {value: 1, viewValue: 'AFTERNOON'},
    {value: 2, viewValue: 'NIGHT'},
  ];

  isLinear = false;

  ngOnInit(): void {
    
  }

  employeeRegister = this.formBuilder.group({
    basic: this.formBuilder.group({
      firstName: this.formBuilder.control('', Validators.required),
      lastName: this.formBuilder.control('', Validators.required),
      birthdayDate: this.formBuilder.control(null)
    }),

    contact: this.formBuilder.group({
      email: this.formBuilder.control('', Validators.required),
      contactNumber: this.formBuilder.control('', Validators.required),
    }),

    professionalData: this.formBuilder.group({
      workNumber: this.formBuilder.control('', Validators.required),
      department: this.formBuilder.control('', Validators.required),
      admissionDate: this.formBuilder.control('', Validators.required),
      recruitmentCompany: this.formBuilder.control('', Validators.required),
      shiftType: this.formBuilder.control('', Validators.required)
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
      birthdayDate: this.datePipe.transform(this.employeeRegister.value.basic?.birthdayDate, 'yyyy-MM-dd'),
      email: this.employeeRegister.value.contact?.email,
      contactNumber: this.employeeRegister.value.contact?.contactNumber,
      workNumber: this.employeeRegister.value.professionalData?.workNumber,
      department: this.employeeRegister.value.professionalData?.department,
      admissionDate: this.datePipe.transform(this.employeeRegister.value.professionalData?.admissionDate, 'yyyy-MM-dd'),
      recruitmentCompany: this.employeeRegister.value.professionalData?.recruitmentCompany,
      shiftType: this.employeeRegister.value.professionalData?.shiftType,
      userRole: this.employeeRegister.value.security?.userRole,
      password: this.employeeRegister.value.security?.password
    };

    this.adminService.post(employeeRegisterFormData).subscribe(
      (res) => {
        console.log("User submited sucessfully: ", res)
      },
      (error) => {
        console.error("Error submiting user: ", error)
      }
    )
  }
}
