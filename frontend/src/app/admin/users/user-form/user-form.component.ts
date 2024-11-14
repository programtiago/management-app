import { Component } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { UserRole } from '../../../model/userRole';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent{

  userRegistrationForm: FormGroup;

  usersRoles: UserRole[] = [
    {value: 0, viewValue: 'ADMIN'}, 
    {value: 1, viewValue: 'EMPLOYEE'}
  ];

  constructor(private formBuilder: FormBuilder){
    this.userRegistrationForm = this.formBuilder.nonNullable.group({
      _id: ['', Validators.required],
      firstName: ['', Validators.required, Validators.minLength(3), Validators.maxLength(20)],
      lastName: ['', Validators.required, Validators.minLength(3), Validators.maxLength(20)],
      workNumber: [null],
      department: ['', Validators.required],
      registryDate: ['', Validators.required],
      isActive: [true],
      userRole: ['', Validators.required],
      email: ['', Validators.required, Validators.minLength(3), Validators.maxLength(20)],
      contactNumber: ['', Validators.required, Validators.minLength(9)],
      updatedAt: ['']
    })
  }  
}
