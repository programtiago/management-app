import { Component, Input, OnInit } from '@angular/core';
import { Department } from '../../../model/department';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ModalInfoDeleteComponent } from '../modal-info-delete/modal-info-delete.component';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-departments-list',
  templateUrl: './departments-list.component.html',
  styleUrl: './departments-list.component.scss'
})
export class DepartmentsListComponent implements OnInit{

  @Input() departments: Department[] = []

  constructor(private router: Router, private route: ActivatedRoute, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar
  ){}

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


  openMenuForDepartments(){
    
  }

  onUpdate(){
    
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
