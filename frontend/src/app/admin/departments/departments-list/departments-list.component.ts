import { Component, Input, OnInit, Output } from '@angular/core';
import { Department } from '../../../model/department/department';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ModalInfoDeleteComponent } from '../modal-info-delete/modal-info-delete.component';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { ModalAssignmentDepartmentUserComponent } from '../modal-assignment-department-user/modal-assignment-department-user.component';
import { User } from '../../../model/user/user';
import { ModalUsersDepartmentComponent } from '../modal-users-department/modal-users-department.component';
import { UserDepartment } from '../../../model/department/user-department/user-department';

@Component({
  selector: 'app-departments-list',
  templateUrl: './departments-list.component.html',
  styleUrl: './departments-list.component.scss'
})
export class DepartmentsListComponent implements OnInit{

  @Input() departments: Department[] = []
  selectedDepartment : any;

  usersOnDepartment: User[] = []
  userAssignmentsDepartment: UserDepartment[] = []
  canShowEmployeesFromDepartment: boolean = false;

  constructor(private router: Router, private route: ActivatedRoute, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar){
  
    this.adminService.getAllUserDepartments().subscribe((res) => {
      if (res != null){
        this.userAssignmentsDepartment = res;
        console.log("ASSIGNMENTS TO DEPARTMENT: ", this.userAssignmentsDepartment)
      }else{
        console.log("THERE IS NO ASSIGNMENTS TO SHOW")
      }
    })
  }


  ngOnInit(): void {
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    })  
 
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }

  refresh(){
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    });
  }


  openModalAssignmentDepartmentUser(department: Department){
    this.adminService.getDepartmentById(department.id).subscribe((res) => {
      this.selectedDepartment = res;
      console.log(res)
    })
    const dialogRef = this.dialog.open(ModalAssignmentDepartmentUserComponent, {
      height: '600px',
      width: '950px',
      data: department
    })   
  }

  openModalWithAllEmployeesByDepartment(departmentId: number){
     this.adminService.getEmployeesByDepartmentId(departmentId).subscribe((res) => {
      if (res != null){
        this.usersOnDepartment = res;
        console.log(res)
      }
      const dialogRef = this.dialog.open(ModalUsersDepartmentComponent, {
        height: '550px',
        width: '900px',
        data: this.usersOnDepartment
      });   
    })
  }

  onDelete(departmentId: number){
    const dialogRef = this.dialog.open(ModalInfoDeleteComponent, {
      height: '160px',
      width: '400px',
      data: 'Are you sure that u want to delete this department ?'
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result){
          this.adminService.deleteDepartmentById(departmentId).subscribe(
            () => {
              this.snackBar.open('Department deleted sucessfully !', 'X', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
              this.refresh();
            },
            () => this.onError("It wasn't possible to delete the department. Please try again or check your internet connection"))}
      })
    }
}
