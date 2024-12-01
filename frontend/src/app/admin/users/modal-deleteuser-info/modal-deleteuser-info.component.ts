import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-deleteuser-info',
  templateUrl: './modal-deleteuser-info.component.html',
  styleUrl: './modal-deleteuser-info.component.scss'
})
export class ModalDeleteuserInfoComponent {

  constructor(public dialogRef: MatDialogRef<ModalDeleteuserInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string){}

  onConfirm(result: boolean): void{
    this.dialogRef.close(result)
  }
}
