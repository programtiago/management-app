import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-modal-confirmation-remove-equipment-user',
  templateUrl: './modal-confirmation-remove-equipment-user.component.html',
  styleUrl: './modal-confirmation-remove-equipment-user.component.scss'
})
export class ModalConfirmationRemoveEquipmentUserComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogDeleteEquipmentFromUser: MatDialogRef<ModalConfirmationRemoveEquipmentUserComponent>){}

  onConfirm(result: boolean){
    this.dialogDeleteEquipmentFromUser.close(result);
  }

}
