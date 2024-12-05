import { Component, Inject, Output, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user';
import { AdminService } from '../../services/admin.service';
import { Equipment } from '../../../model/equiment';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserEquipment } from '../../../model/user-equipment';
import { Router } from '@angular/router';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatOption, MatSelect } from '@angular/material/select';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-modal-users-assignment-equipment',
  templateUrl: './modal-users-assignment-equipment.component.html',
  styleUrl: './modal-users-assignment-equipment.component.scss'
})
export class ModalUsersAssignmentEquipmentComponent {

  equipments: Equipment[] = [];
  selectedEquipmentId!: number;
  selectedEquipment!: Equipment;
  userEquipment!: UserEquipment;
  errorMessage!: string;
  
  canChooseMultipleEquipments!: boolean
  canLoadInformationCardAssignment!: boolean;
  wasClaredMatSelect!: boolean

  @ViewChild('matRef') matRef!: MatSelect;
  public cMultiCtrl: FormControl = new FormControl();

  constructor(public dialogRef: MatDialogRef<ModalUsersAssignmentEquipmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User, private adminService: AdminService, 
  private dialog: MatDialog, private snackbar: MatSnackBar, private router: Router){
      this.canChooseMultipleEquipments = false;
      
      this.adminService.getUserById(data.id).subscribe((res) => {
        data = res;
      })

      this.adminService.getEquipments().subscribe((res) => {
        this.equipments = res;
      });
    }

    clear(){
      this.cMultiCtrl.reset();
      this.wasClaredMatSelect = true;
    }
  
    onError(errorMsg: string){
      this.dialog.open(ErrorDialogComponent, {
        data: 
          errorMsg
      })
    }

    onConfirm(result: boolean){
      if (result){
        console.log(this.selectedEquipment)
        this.adminService.assignEquipmentToUser(this.data.id, this.selectedEquipmentId).subscribe({
          next: (res) => {
            this.userEquipment = res;
            if (res.id != null){
              this.snackbar.open('Equipment assigned sucessfully', 'X', { duration: 2000 });
              this.dialogRef.close();
            }
          },
          error: (error) => {
            this.errorMessage = error.error.errors
            this.onError(this.errorMessage)
          }  
        })
      }else{
        this.dialogRef.close();
      }
    }

    changeEquipment(value: number){
      this.selectedEquipmentId = value;

        if (this.wasClaredMatSelect){
          this.wasClaredMatSelect = false;
        }
        this.adminService.getEquipmentById(this.selectedEquipmentId).subscribe((res) => {
          this.selectedEquipment = res;
          if (res.id != null)
            this.canLoadInformationCardAssignment = true
            this.dialogRef.updateSize('750px', '550px') //if loads the equipment information withou error updates modal size
          })
        console.log(this.canLoadInformationCardAssignment)
      
    }

     

    onChangeMultipleEquipments(event: MatCheckboxChange){
      if (event.checked){
        this.canChooseMultipleEquipments = true
      }
      this.canChooseMultipleEquipments = false
    }
}
