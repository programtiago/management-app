import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Equipment } from '../../../model/equiment';

@Component({
  selector: 'app-modal-question-delete-equipment',
  templateUrl: './modal-question-delete-equipment.component.html',
  styleUrl: './modal-question-delete-equipment.component.scss'
})
export class ModalQuestionDeleteEquipmentComponent {

  constructor(public dialogRef: MatDialogRef<ModalQuestionDeleteEquipmentComponent>,
      @Inject(MAT_DIALOG_DATA) public data: Equipment){}

  onConfirm(){
    this.dialogRef.close();
  }    
  
}
