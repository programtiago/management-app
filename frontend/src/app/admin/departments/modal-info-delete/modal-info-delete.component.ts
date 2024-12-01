import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-info-delete',
  templateUrl: './modal-info-delete.component.html',
  styleUrl: './modal-info-delete.component.scss'
})
export class ModalInfoDeleteComponent {

  constructor(public dialogRef: MatDialogRef<ModalInfoDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string){}

  onConfirm(result: boolean): void{
    this.dialogRef.close(result)
  }

}
