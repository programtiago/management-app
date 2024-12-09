import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { UserEquipment } from '../../../model/user-equipment';

@Component({
  selector: 'app-modal-information-equipment-user-owner',
  templateUrl: './modal-information-equipment-user-owner.component.html',
  styleUrl: './modal-information-equipment-user-owner.component.scss'
})
export class ModalInformationEquipmentUserOwnerComponent {

  userEquipments: UserEquipment[] = []
  selectedUser!: User

  constructor(
    public dialogRef: MatDialogRef<ModalInformationEquipmentUserOwnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private adminService: AdminService
  ) {
   this.adminService.getEquipmentsByUserId(data.at(0)).subscribe((res) => {
    this.userEquipments = res
   })

   this.adminService.getUserById(data.at(0)).subscribe((res) => {
    this.selectedUser = res
   })
  }

  onClose():void{
    this.dialogRef.close();
  }
}
