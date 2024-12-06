import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ModalInformationComponent } from '../../modal-information/modal-information.component';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { ModalDeleteuserInfoComponent } from '../modal-deleteuser-info/modal-deleteuser-info.component';
import { ModalUsersAssignmentEquipmentComponent } from '../modal-users-assignment-equipment/modal-users-assignment-equipment.component';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  @Input() users: User[] = []

  selectedUser: any;
  selectedUserId : any;
  
  questionForChangingStatus: string = "";
  questionForActivate: string = "This will activate the account user. Would u like to proceed ?";
  questionForDesativate: string = "This will deactivate the user account. Would u like to proceed ?";

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'birthdayDate', 'actions'
  ]

  constructor(private router: Router, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar
  ){}

  ngOnInit(): void {
   
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }

  refresh(){
    this.adminService.listUsers().subscribe((res) => {
      this.users = res;
    });
  }

  onAdd(){
    this.router.navigate(['admin/new'])
  }

  onChangeUserStatus(user: User){
    this.selectedUserId = user.id;

    if (user.isActive == false){
      this.questionForChangingStatus = this.questionForActivate;
    }else if (user.isActive == true){
      this.questionForChangingStatus = this.questionForDesativate;
    }

    const dialogRef = this.dialog.open(ModalInformationComponent, {
      height: '200px',
      width: '400px',
      data: [this.questionForChangingStatus, this.selectedUserId]
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result){
        if (user.isActive == false){
          this.adminService.activateUser(user.id).subscribe(
            () => {
              this.snackBar.open('User activated sucessfully !', 'X', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
              this.refresh();
            },
            () => this.onError('Occurred a error. Please try again or check your internet connection')
          )
        }else if (user.isActive == true){
          this.adminService.deactivateUser(user.id).subscribe(
            () => {
              this.snackBar.open('User deactivated sucessfully !', 'X', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
              this.refresh();
            },
            () => this.onError('Occurred a error. Please try again or check your internet connection')
          )
        }
      }
    })
  }

  onUpdate(userId: number){
    this.router.navigate(['admin/edit', userId]);
  }

  onDelete(userId: number){
    const dialogRef = this.dialog.open(ModalDeleteuserInfoComponent, {
      height: '160px',
      width: '400px',
      data: 'Are you sure that u want to delete this user ?'
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result){
          this.adminService.deleteUserById(userId).subscribe(
            () => {
              this.snackBar.open('User deleted sucessfully !', 'X', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
              this.refresh();
            },
            () => this.onError("It wasn't possible to delete the user. Please try again or check your internet connection"))}
      })
    }

    openMenuForEquipmentAssignment(user: User){
      const dialogRef = this.dialog.open(ModalUsersAssignmentEquipmentComponent, {
        height: '400px',
        width: '750px',
        data: user
      });
    }
}
