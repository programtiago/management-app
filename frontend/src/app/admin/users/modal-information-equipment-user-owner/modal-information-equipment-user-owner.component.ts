import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user';
import { Equipment } from '../../../model/equiment';

@Component({
  selector: 'app-modal-information-equipment-user-owner',
  templateUrl: './modal-information-equipment-user-owner.component.html',
  styleUrl: './modal-information-equipment-user-owner.component.scss'
})
export class ModalInformationEquipmentUserOwnerComponent {
  constructor(
    public dialogRef: MatDialogRef<ModalInformationEquipmentUserOwnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Equipment[]
  ) {}

  onClose():void{
    this.dialogRef.close();
  }
}
