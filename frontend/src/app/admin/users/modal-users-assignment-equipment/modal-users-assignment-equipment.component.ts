import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { catchError, Observable, of } from 'rxjs';
import { Equipment } from '../../../model/equiment';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-modal-users-assignment-equipment',
  templateUrl: './modal-users-assignment-equipment.component.html',
  styleUrl: './modal-users-assignment-equipment.component.scss'
})
export class ModalUsersAssignmentEquipmentComponent {

  equipments: Equipment[] = [];
  selectedEquipmentId!: number;

  constructor(public dialogRef: MatDialogRef<ModalUsersAssignmentEquipmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User, private adminService: AdminService, 
  private dialog: MatDialog){
      this.adminService.getUserById(data.id).subscribe((res) => {
        data = res;
      })

      this.adminService.getEquipments().subscribe((res) => {
        this.equipments = res;
      }
      );
    }
  
    onError(errorMsg: string){
      this.dialog.open(ErrorDialogComponent, {
        data: 
          errorMsg
      })
    }

    onConfirm(){
      this.adminService.assignEquipmentToUser(this.data.id, this.selectedEquipmentId);
    }

    changeEquipment(value: number){
      this.selectedEquipmentId = value;
    }
}
