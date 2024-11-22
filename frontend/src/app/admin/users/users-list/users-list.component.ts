import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ModalInformationComponent } from '../../modal-information/modal-information.component';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  @Input() users: User[] = []
  selectedUserId! : string;
  
  questionForChangingStatus: string = "";
  questionForActivate: string = "This will ativate the account user. Would u like to proceed ?";
  questionForDesativate: string = "This will deactivate the user account. Would u like to proceed ?";

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'department', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'updatedAt', 'birthdayDate', 'actions'
  ]

  constructor(private router: Router, private route: ActivatedRoute, private dialog: MatDialog,){}

  ngOnInit(): void {}

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route})
  }

  onChangeUserStatus(user: User){
    this.selectedUserId = user._id;

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
  }
}
