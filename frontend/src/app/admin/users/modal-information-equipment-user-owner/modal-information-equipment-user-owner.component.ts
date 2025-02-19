import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user/user';
import { UserEquipment } from '../../../model/user-equipment/user-equipment';
import { ModalConfirmationRemoveEquipmentUserComponent } from '../modal-confirmation-remove-equipment-user/modal-confirmation-remove-equipment-user.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-modal-information-equipment-user-owner',
  templateUrl: './modal-information-equipment-user-owner.component.html',
  styleUrl: './modal-information-equipment-user-owner.component.scss'
})
export class ModalInformationEquipmentUserOwnerComponent implements OnInit{

  userEquipments: UserEquipment[] = []
  selectedUser: User | undefined
  selectedUserId!: number

  modalWidth: number = 1000;
  modalHeight: number = 500;

  displayedColumns: String[] = ['description', 'serialNumber', 'brand', 'model', 'registryDate', 'allocationDateTime', 'actions'
  ]

  constructor(
    public dialogRef: MatDialogRef<ModalInformationEquipmentUserOwnerComponent>,
    public dialogDeleteEquipmentFromUser: MatDialogRef<ModalConfirmationRemoveEquipmentUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: number, userEquipments: any}, private adminService: AdminService, private dialog: MatDialog,
    private snackbar: MatSnackBar
  ) {
   this.adminService.getEquipmentsByUserId(data.userId).subscribe((res) => {
    this.userEquipments = res
   })

   this.adminService.getUserById(data.userId).subscribe((res) => {
    this.selectedUser = res
    this.selectedUserId = data.userId
   })
  }

  updateModalSize(): void{
    if (this.userEquipments){
      this.modalWidth = this.userEquipments.length > 1 ? 1000 : 500
      this.modalHeight = this.userEquipments.length > 1 ? 500 : 300
    }
  }

  
  onError(errorMsg: string){
    this.dialog.open(ErrorDialogComponent, {
      data: 
        errorMsg
    })
  }

  openConfirmationModalRemoveEquipment(equipmentId: number){
    const dialogDeleteEquipmentFromUser = this.dialog.open(ModalConfirmationRemoveEquipmentUserComponent, {
      width: '300px',
      height: '210px',
      data: this.selectedUserId
    });

    dialogDeleteEquipmentFromUser.afterClosed().subscribe((res : boolean) => {
      if (res){
        this.adminService.returnEquipmentFromUser(this.selectedUserId, equipmentId).subscribe(
          () => {
            this.snackbar.open('Equipment removed from user sucessfully. Now the equipment is available !', 'X', {
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

  ngOnInit(): void {
    this.updateModalSize();
  }

  onClose():void{
    this.dialogRef.close();
  }

  refresh(){
    this.adminService.getEquipmentsByUserId(this.data.userId).subscribe((res) => {
      this.userEquipments = res;
    })
  }

  //Function responsible to return the equipment object from a user object. The equipment attribuiton is deleted from that user and becomes AVAILABLE for assignment 
  returnEquipmentFromUser(userId: number, equipmentId: number){
    this.adminService.returnEquipmentFromUser(userId, equipmentId).subscribe((res) => {
      this.refresh();
    })
  }
}
