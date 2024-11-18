import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserRole } from '../../../model/userRole';
import { Department } from '../../../model/department';
import { ShiftType } from '../../../model/ShiftType';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  constructor(private formBuilder: FormBuilder){}

  //TEMPORARILY. Further we need to catch from the API
  usersRoles: UserRole[] = [
    {value: 0, viewValue: 'ADMIN'}, 
    {value: 1, viewValue: 'EMPLOYEE'}
  ];

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
    basic:this.formBuilder.group({
      firstName: this.formBuilder.control('', Validators.required),
      lastName: this.formBuilder.control('', Validators.required),
      birthdayDate: this.formBuilder.control('', Validators.required)
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
      userRole: this.formBuilder.control('', Validators.required),
      password: this.formBuilder.control('', Validators.required)
    })
  })

}
