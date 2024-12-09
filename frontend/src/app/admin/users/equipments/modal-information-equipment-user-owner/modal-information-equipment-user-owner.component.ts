import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../../model/user';
import { AdminService } from '../../../services/admin.service';
import { UserEquipment } from '../../../../model/user-equipment';

@Component({
  selector: 'app-modal-information-equipment-user-owner',
  templateUrl: './modal-information-equipment-user-owner.component.html',
  styleUrl: './modal-information-equipment-user-owner.component.scss'
})
export class ModalInformationEquipmentUserOwnerComponent {

  userEquipments: UserEquipment[] = []
  selectedUser: User | undefined
  selectedUserId!: number

  constructor(
    public dialogRef: MatDialogRef<ModalInformationEquipmentUserOwnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private adminService: AdminService
  ) {
   this.adminService.getEquipmentsByUserId(data.at(0)).subscribe((res) => {
    this.userEquipments = res

    if (this.userEquipments.length < 1){
      this.dialogRef.updateSize('400px', '200px')
    }
   })

   this.adminService.getUserById(data.at(0)).subscribe((res) => {
    this.selectedUser = res
    this.selectedUserId = data.at(0)
   })
  }

  onClose():void{
    this.dialogRef.close();
  }

  //Function responsible to return the equipment object from a user object. The equipment attribuiton is deleted from that user and becomes AVAILABLE for assignment 
  returnEquipmentFromUser(userId: number, equipmentId: number){
    this.adminService.returnEquipmentFromUser(userId, equipmentId).subscribe((res) => {
      console.log(res)
    })
  }
}
