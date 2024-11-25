import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-modal-deleteuser-info',
  templateUrl: './modal-deleteuser-info.component.html',
  styleUrl: './modal-deleteuser-info.component.scss'
})
export class ModalDeleteuserInfoComponent {

  constructor(public dialogRef: MatDialogRef<ModalDeleteuserInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string, private adminService: AdminService){}

  onConfirm(result: boolean): void{
    this.dialogRef.close(result)
  }
}
