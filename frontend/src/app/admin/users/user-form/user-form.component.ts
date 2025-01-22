import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { UserRole } from '../../../model/user/userRole';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DatePipe, Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDatepickerInput } from '@angular/material/datepicker';

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

  constructor(private fb: FormBuilder, private adminService: AdminService,
    private location: Location, private matSnackBar: MatSnackBar, private route: ActivatedRoute,
    private router: Router, private datePipe: DatePipe
  ){
    this.userUpdateForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      birthdayDate: ['', Validators.required],
      workNumber: ['', [Validators.required, Validators.min(3000), Validators.max(100000)]],
      nif: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
      recruitmentCompany: ['', Validators.required],
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

      let birthdayDate: Date = new Date(res.birthdayDate)
      console.log("Data loadded: ", birthdayDate)

      this.userUpdateForm.patchValue({
        firstName: res.firstName,
        lastName: res.lastName,
        workNumber: res.workNumber,
        birthdayDate: birthdayDate,
        nif: res.nif,
        recruitmentCompany: res.recruitmentCompany,
        userRole: res.userRole,
        email: res.email,
        contactNumber: res.contactNumber,
        password: res.password
      })
    })
  }

  onUpdate(): void{
    this.adminService.updateUser(this.userUpdateForm.value, this.userId).subscribe(() => {
      this.onSucessUpdatedUser();
    }, error => {
      this.onError();
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
    this.matSnackBar.open('Error trying to update user', '', { duration: 5000 });
  }
}

