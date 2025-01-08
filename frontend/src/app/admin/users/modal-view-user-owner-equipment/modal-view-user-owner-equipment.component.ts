import { Component, Inject } from '@angular/core';
import { User } from '../../../model/user/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-view-user-owner-equipment',
  templateUrl: './modal-view-user-owner-equipment.component.html',
  styleUrl: './modal-view-user-owner-equipment.component.scss'
})
export class ModalViewUserOwnerEquipmentComponent {

   constructor(public dialogRef: MatDialogRef<ModalViewUserOwnerEquipmentComponent>,
        @Inject(MAT_DIALOG_DATA) public user: User){}

    onOk(){
      this.dialogRef.close();
    }
}
