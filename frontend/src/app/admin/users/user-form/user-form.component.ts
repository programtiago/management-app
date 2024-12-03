import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { UserRole } from '../../../model/userRole';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent implements OnInit{

  userRoles = UserRole;
  userId!: any
  userSelected: any;
  userUpdateForm!: FormGroup;

  /*
  usersRoles: UserRole[] = [
    {value: 0, viewValue: 'ADMIN'}, 
    {value: 1, viewValue: 'EMPLOYEE'}
  ];
  */

  constructor(private fb: FormBuilder, private adminService: AdminService,
    private location: Location, private matSnackBar: MatSnackBar, private route: ActivatedRoute,
    private router: Router
  ){
    this.userUpdateForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      birthdayDate: ['', [Validators.required]],
      workNumber: [null, [Validators.required, Validators.min(3000), Validators.max(100000)]],
      department: ['', Validators.required],
      userRole: ['', Validators.required],
      email: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      contactNumber: ['', [Validators.required, Validators.minLength(9)]],
      password: ['', [Validators.required, Validators.minLength(9)]],
    })
  }  

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('id');

    this.adminService.getUserById(this.userId).subscribe((res) => {
      this.userSelected = res;
      this.userUpdateForm.patchValue({
        firstName: res.firstName,
        lastName: res.lastName,
        birthdatyDate: res.birthdayDate,
        workNumber: res.workNumber,
        department: res.department.description,
        userRole: res.userRole,
        email: res.email,
        contactNumber: res.contactNumber,
        password: res.password
      })
    })
  }

  onUpdate(): void{
    this.adminService.updateUser(this.userUpdateForm.value, this.userId).subscribe((res) => {
      this.onSucessUpdatedUser();
      this.router.navigateByUrl("")
      if (res == null)
        this.onError()
      else
        console.log(res)
    })
  }

  onCancel(){
    this.location.back();
  }

  private onSucessUpdatedUser(){
    this.matSnackBar.open('User updated sucessfully', 'X', { duration: 5000 });
    this.onCancel();
  }

  private onError(){
    this.matSnackBar.open('Error trying to create user', '', { duration: 5000 });
  }
}
