import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { User } from '../../../model/user/user';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ModalInformationComponent } from '../../modal-information/modal-information.component';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { ModalDeleteuserInfoComponent } from '../modal-deleteuser-info/modal-deleteuser-info.component';
import { ModalUsersAssignmentEquipmentComponent } from '../modal-users-assignment-equipment/modal-users-assignment-equipment.component';
import { ModalInformationEquipmentUserOwnerComponent } from '../modal-information-equipment-user-owner/modal-information-equipment-user-owner.component';
import { UserEquipment } from '../../../model/user-equipment/user-equipment';
import { Equipment } from '../../../model/equipment/equiment';
import { ModalHistoryLogByuserComponent } from '../modal-history-log-byuser/modal-history-log-byuser.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit{

  @Input() users: User[] = []
  @Output() filteredUsers = new EventEmitter<{users: User[]; totalElements: number}>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  searchKeyword: string = '';

  dataSource = new MatTableDataSource<User>(this.users);
  searchResult: undefined | User[]

  userEquipmentsByUser: UserEquipment[] = [];

  selectedUser!: User | undefined;
  selectedUserId : any;
  errorMessage: string = "";

  equipmentsAvailable: Equipment[] = []
  
  questionForChangingStatus: string = "";
  questionForActivate: string = "This will activate the account user. Would u like to proceed ?";
  questionForDesativate: string = "This will deactivate the user account. Would u like to proceed ?";

  displayedColumns: String[] = ['firstName', 'lastName', 'workNumber', 'registryDate', 'isActive', 
    'userRole',  'email', 'contactNumber', 'birthdayDate', 'actions'
  ]

  isLoading: boolean = false;

  constructor(private router: Router, private dialog: MatDialog,
    private adminService: AdminService, private snackBar: MatSnackBar, private activeRoute: ActivatedRoute
  ){
    this.adminService.getEquipmentsAvailable().subscribe((res) =>  {
      this.equipmentsAvailable = res;
    })
  }

  ngOnInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }

  refresh(){
    this.adminService.listAllUsers().subscribe((res) => {
      this.users = res.content;
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
          this.adminService.activateUserById(user.id).subscribe(
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
      }
    })
  }

  onUpdate(userId: number){
    this.router.navigate(['admin/edit', userId]);
  }

  onDelete(userId: number){
    const dialogRef = this.dialog.open(ModalDeleteuserInfoComponent, {
      height: '210px',
      width: '400px',
      data: 'Are you sure that u want to deactivate this user ?'
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result){
          this.adminService.deactivateUserById(userId).subscribe(
            () => {
              this.snackBar.open('User deactivated sucessfully !', 'X', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
              this.refresh();
            },
            () => this.onError(this.errorMessage))}
      })
    }

    openMenuForEquipmentAssignment(user: User){
      this.adminService.getEquipmentsAvailable().subscribe({
        next: (res) => {
          this.equipmentsAvailable = res;
          this.errorMessage = "";
        },
        error: (error) => {
          this.errorMessage = error;
          this.onError(this.errorMessage)
        }
      })

      if (this.equipmentsAvailable.length != 0){
        const dialogRef = this.dialog.open(ModalUsersAssignmentEquipmentComponent, {
          //height: '70%',
          //width: '80%',
          autoFocus: false,
          hasBackdrop: false,
          enterAnimationDuration: 1000,
          exitAnimationDuration: 1000,
          panelClass: ['dialog-equipment-assignment-user-style'],  
          data: user
        });
      }else{
        this.onError('No equipments available to assign !');
      }
    }

    //Information Modal that renders all the equipments by user. 
    openModalViewEquipmentUserOwner(userId: number){
      this.adminService.getUserById(userId).subscribe((res) => {
        if (res.userEquipments.length > 0){
          this.selectedUserId = res.id  //store id in a variable
          this.userEquipmentsByUser = res.userEquipments //store the objects user Equipments by user
          this.dialog.open(ModalInformationEquipmentUserOwnerComponent, {
            data: { userId: this.selectedUserId, userEquipments: this.userEquipmentsByUser }
          });
        }else{
          this.onError("There are no equipments available for this user !")
        }
      }, (error) => {
        this.onError("Error fetching equipments ! ")
    });
  }

  openModalHistoryLogByUserId(userId: number){
    console.log(userId);
    this.dialog.open(ModalHistoryLogByuserComponent, {
      disableClose: true,
      autoFocus: false,
      width: '50%',
      height: '50%',
      enterAnimationDuration: 1000,
      exitAnimationDuration: 2000
    })
  }

  applyFilter(event: Event){
    const filterValue = (event.target as HTMLInputElement).value;
    this.searchKeyword = filterValue.trim();
    
    const pageSize = this.paginator ? this.paginator.pageSize : 10;
    this.searchUsersByKeyword(0, pageSize)
  }

  filterByStatusActive(){
    this.adminService.listUsersActive().subscribe((res) => {
      this.users = res.content;
      this.dataSource.data = this.users;
      this.filteredUsers.emit({ users: this.users, totalElements: res.totalElements})
    });
  }

  filterByStatusNotActive(){
    this.adminService.listUsersNotActive().subscribe((res) => {
      this.users = res.content;
      this.dataSource.data = this.users;
      this.filteredUsers.emit({ users: this.users, totalElements: res.totalElements})
    });
  }

  filterByAllStatuts(){
    this.adminService.listAllUsers().subscribe((res) => {
      this.users = res.content;
      this.dataSource.data = this.users;
      this.filteredUsers.emit({ users: this.users, totalElements: res.totalElements})
    });
  }

  filterByRole(role: string){
    this.adminService.filterUsersByRole(role).subscribe((res) => {
      this.users = res.content
      this.dataSource.data = this.users;
      this.filteredUsers.emit({ users: this.users, totalElements: res.totalElements})
    });
  }

  filterByAll(){
    this.adminService.listAllUsers().subscribe((res) => {
      this.users = res.content
      this.dataSource.data = this.users;
      this.filteredUsers.emit({ users: this.users, totalElements: res.totalElements})
    })
  }

  searchUsersByKeyword(pageIndex: number = 0, pageSize: number = 10){
    if (this.searchKeyword.trim() !== ''){
      this.adminService.searchUsers(this.searchKeyword, pageIndex, pageSize)
        .subscribe((res) => {
          this.users = res.content;
          this.dataSource.data = this.users;
          this.filteredUsers.emit({users: this.users, totalElements: res.totalElements});
          if (this.paginator){
            this.paginator.length = res.totalElements;
            this.paginator.pageIndex = pageIndex;
          }
        });
    }else{
      this.filterByAll();
    }
  }

  onPageChange(event: PageEvent){
    if (this.searchKeyword.trim() != ''){
      this.searchUsersByKeyword(event.pageIndex, event.pageSize);
    }else{
      this.adminService.listAllUsers(event.pageIndex, event.pageSize).subscribe((res) => {
        this.users = res.content;
        this.dataSource.data = this.users;
        this.filteredUsers.emit({users: this.users, totalElements: res.totalElements})
      })
    }
  }
}
