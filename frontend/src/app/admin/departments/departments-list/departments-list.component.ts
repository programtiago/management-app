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
import { Observable } from 'rxjs';

@Component({
  selector: 'app-departments-list',
  templateUrl: './departments-list.component.html',
  styleUrl: './departments-list.component.scss'
})
export class DepartmentsListComponent implements OnInit{

  @Input() departments: Department[] = []
  departments$!: Observable<Department[]>;
  selectedDepartment : any;
  departmentId!: number;

  //variable responsible to load the dialog to assign employees to departemnt or shown a dialog with error that does not exists no users available
  existsEmployeesAvailable: boolean = false

  usersOnDepartment: User[] = []
  userAssignmentsDepartment: UserDepartment[] = []
  canShowEmployeesFromDepartment: boolean = false;

  constructor(private route: ActivatedRoute, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar){
  
    this.adminService.getAllUserDepartments().subscribe((res) => {
      if (res != null){
        this.userAssignmentsDepartment = res;
      }
    })
  }

  ngOnInit(): void {
    this.adminService.listDepartments().subscribe(departments => {
      this.departments = departments;
    });
  }

  checkIfExistsAvailableEmployeesForDepartment(): Promise<boolean>{
    return new Promise((resolve, reject) => {
      this.adminService.listUsersAvailableAssignToDepartment().subscribe((res) => {
        if (res == null || res.length == 0){
          this.existsEmployeesAvailable = false;
          resolve(false);
        }else{
          this.existsEmployeesAvailable = true;
          resolve(true);
        }
      }, (error) => {
        this.existsEmployeesAvailable = false;
        this.onError("Error checking available employees !")
        reject(false);
      });
    });
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }

  loadDepartments(){
    this.adminService.listDepartments().subscribe((res) => {
      this.departments = res;
    });
  }

  async openModalAssignmentDepartmentUser(department: Department){
    const exists = await this.checkIfExistsAvailableEmployeesForDepartment(); //method to handle assyncronous operation

    if (exists){
      this.adminService.getDepartmentById(department.id).subscribe((res) => {
        this.selectedDepartment = res;
        const dialogRef = this.dialog.open(ModalAssignmentDepartmentUserComponent, {
          height: '750px',
          width: '950px',
          data: department
        })
        // Subscribe to afterClosed() to reload departments once the dialog is closed
        dialogRef.afterClosed().subscribe(res => {
          this.loadDepartments();
        });
      });
    }else{
      this.onError("No employees available to assign !")
    }
  }

  openModalWithAllEmployeesByDepartment(departmentId: number){
    this.adminService.getEmployeesByDepartmentId(departmentId).subscribe((res) => {
      if (res.length > 0){
        this.usersOnDepartment = res;
        const dialogRef = this.dialog.open(ModalUsersDepartmentComponent, {
          height: '420px',
          width: '900px',
          data: { users: this.usersOnDepartment, departmentId: departmentId },
          disableClose: false
        });   

        dialogRef.afterClosed().subscribe(() => {
          this.loadDepartments();
        });
      }else{
        this.usersOnDepartment = []
        this.onError("No employees to show on this department ! ")
      }
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
              this.loadDepartments();
            },
            () => this.onError("It wasn't possible to delete the department. Please try again or check your internet connection"))}
      })
    }
}
