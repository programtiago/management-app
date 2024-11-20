import { Component } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { UserRole } from '../../../model/userRole';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent{

  output: any;

  userRegistrationForm: FormGroup;

  /*
  usersRoles: UserRole[] = [
    {value: 0, viewValue: 'ADMIN'}, 
    {value: 1, viewValue: 'EMPLOYEE'}
  ];
  */

  constructor(private formBuilder: FormBuilder, private adminService: AdminService,
    private location: Location, private matSnackBar: MatSnackBar
  ){
    this.userRegistrationForm = this.formBuilder.nonNullable.group({
      _id: ['', Validators.required],
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      workNumber: [null, [Validators.required, Validators.min(3000), Validators.max(100000)]],
      department: ['', Validators.required],
      registryDate: ['', Validators.required],
      isActive: [true],
      userRole: ['', Validators.required],
      email: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      contactNumber: ['', [Validators.required, Validators.minLength(9)]],
      updatedAt: ['']
    })
  }  

  /*
  onSubmit(userData: User){
    this.adminService.post(userData).subscribe(
      result => {
        this.onSucess();
      },
      error => {
        this.onError();
      }
    )
  }
    */

  onUserRoleSelection(event: any){
    this.output = event.value;
    console.log(this.output)
  }
  onCancel(){
    this.location.back();
  }

  private onSucess(){
    this.matSnackBar.open('User created sucessfully', '', { duration: 5000 });
    this.onCancel();
  }

  private onError(){
    this.matSnackBar.open('Error trying to create user', '', { duration: 5000 });
  }
}
