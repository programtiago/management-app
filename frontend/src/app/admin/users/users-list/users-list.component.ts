import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ModalInformationComponent } from '../../modal-information/modal-information.component';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  @Input() users: User[] = []
  @Output() onChangeStatus = new EventEmitter(false)

  selectedUserId : any;
  
  questionForChangingStatus: string = "";
  questionForActivate: string = "This will activate the account user. Would u like to proceed ?";
  questionForDesativate: string = "This will deactivate the user account. Would u like to proceed ?";

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'updatedAt', 'birthdayDate', 'actions'
  ]

  constructor(private router: Router, private route: ActivatedRoute, private dialog: MatDialog,
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
    this.adminService.list().subscribe((res) => {
      this.users = res;
    });
  }

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }

  onChangeUserStatus(user: User){
    this.selectedUserId = user.id;
    console.log(user.id)

    if (user.active == false){
      this.questionForChangingStatus = this.questionForActivate;
    }else if (user.active == true){
      this.questionForChangingStatus = this.questionForDesativate;
    }

    const dialogRef = this.dialog.open(ModalInformationComponent, {
      height: '200px',
      width: '400px',
      data: [this.questionForChangingStatus, this.selectedUserId]
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result){
        if (user.active == false){
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
        }else if (user.active == true){
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

}
