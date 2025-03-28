import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-modal-information',
  templateUrl: './modal-information.component.html',
  styleUrl: './modal-information.component.scss'
})
export class ModalInformationComponent implements OnInit{

  actionForDeactivate: string = "Deactivate";
  actionForAtivate: string = "Activate";
  statusUser: any;
  response: any;
  userId: any;

  constructor(public dialogRef: MatDialogRef<ModalInformationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private adminService: AdminService
  ){}

  onConfirm(result: boolean): void{
    this.dialogRef.close(result);
  }

  ngOnInit(): void {
    this.userId = this.data.at(1)
    this.response = this.adminService.getUserById(this.userId).subscribe((res) => {
      this.statusUser = res.isActive;
    });
  } 
}
