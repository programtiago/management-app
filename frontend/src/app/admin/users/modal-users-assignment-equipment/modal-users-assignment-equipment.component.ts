import { Component, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../model/user/user';
import { AdminService } from '../../services/admin.service';
import { Equipment } from '../../../model/equipment/equiment';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserEquipment } from '../../../model/user-equipment/user-equipment';
import { Router } from '@angular/router';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatSelect } from '@angular/material/select';
import { FormControl } from '@angular/forms';
import { catchError, Observable, of } from 'rxjs';

@Component({
  selector: 'app-modal-users-assignment-equipment',
  templateUrl: './modal-users-assignment-equipment.component.html',
  styleUrl: './modal-users-assignment-equipment.component.scss'
})
export class ModalUsersAssignmentEquipmentComponent implements OnInit {

  equipmentsAvailable: Equipment[] = [];
  equipmentsAvailable$: Observable<Equipment[]>;
  
  selectedEquipment!: Equipment;
  selectedEquipment$?: Observable<Equipment>;


  userEquipment!: UserEquipment;
  userEquipments: UserEquipment[] = []

  errorMessage!: string;
  
  canChooseMultipleEquipments!: boolean
  canLoadInformationCardAssignment!: boolean;
  wasClearedMatSelect!: boolean;

  selectedEquipmentId!: number;
  selectedMultipleEquipmentsIds: number[] = []
  selectedMultipleEquipmentsList: Equipment[] = []

  widthModalAssingment: string = '750px';
  heightModalAssignment: string = '400px';


  @ViewChild('matRef') matRef!: MatSelect;
  public cMultiCtrl: FormControl = new FormControl();

  constructor(public dialogRef: MatDialogRef<ModalUsersAssignmentEquipmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User, private adminService: AdminService, 
      private dialog: MatDialog, private snackbar: MatSnackBar, private router: Router){
      this.canChooseMultipleEquipments = false;
      
      this.adminService.getUserById(data.id).subscribe((res) => {
        data = res;
      })

      this.adminService.getEquipmentsAvailable().subscribe((res) => {
        this.equipmentsAvailable = res;
      });

      this.equipmentsAvailable$ = this.adminService.getEquipmentsAvailable().pipe(
        catchError(error => {
          this.onError("Error loading equipments " + error.error.errors);
          return of ([])
        })
        );

        this.equipmentsAvailable$ = this.adminService.getEquipmentsAvailable().pipe(
          catchError(error => {
            this.onError("Error loading equipments " + error.error.errors);
            return of ([])
          })
          );
    }

    ngOnInit(): void {
      this.updateModalAssignmentSize(); 
      this.equipmentsAvailable$.subscribe((res) => {
        this.equipmentsAvailable = res;
        console.log(res)
      })
    }

    updateModalAssignmentSize():void{
      if (this.equipmentsAvailable.length < 1){
        this.dialogRef.updateSize(this.widthModalAssingment, this.heightModalAssignment)
      }   
    }

    clear(){
      this.cMultiCtrl.reset();
      this.wasClearedMatSelect = true;
      this.dialogRef.updateSize('750px', '400px') 
    }

    clearMultipleSelection(){
      this.cMultiCtrl.reset(); 
      if (this.cMultiCtrl.value == null){
        this.dialogRef.updateSize('750px', '400px') 
        this.canLoadInformationCardAssignment = false
      }
    }
  
    onError(errorMsg: string){
      this.dialog.open(ErrorDialogComponent, {
        data: 
          errorMsg
      })
    }

    onConfirm(result: boolean){
      if (result){
        if(!this.canChooseMultipleEquipments){
          this.adminService.assignEquipmentToUser(this.data.id, this.selectedEquipmentId).subscribe({
            next: (res) => {
              this.userEquipment = res;
              console.log("Resposta" + res)
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
          this.adminService.assignMultipleEquipmentsToUser(this.data.id, this.selectedMultipleEquipmentsIds).subscribe({
            next: (res) => {
              this.userEquipments = res;
              if (res != null){
                this.canLoadInformationCardAssignment = true;
                this.snackbar.open('The equipments were assigned sucessfully.', 'X')
                this.dialogRef.close();
                this.navigateToEquipments();
              }
            },
            error: (error) => {
              this.errorMessage = error.error.errors
              this.onError(this.errorMessage)
            }  
        })  
        }
      }else{
        this.dialogRef.close()
      }  
    }
    
    //For choosing a single equipment
    changeEquipment(value: number){
      this.selectedEquipmentId = value;

      this.selectedEquipment$ = this.adminService.getEquipmentById(this.selectedEquipmentId);
      this.selectedEquipment$.subscribe((res) => {
        console.log("RESPOSTA PRONTA: ", res)
      })
      
      if (!this.canChooseMultipleEquipments){
        this.adminService.getEquipmentById(this.selectedEquipmentId).subscribe((res) => {
          this.selectedEquipment = res;
          console.log(res)
          if (res.id != null)
            this.canLoadInformationCardAssignment = true
          })
          
      }
      this.dialogRef.updateSize('750px', '600px') //if loads the equipment information withou error updates modal size
    }

     //For choosing multiple equipments 
    changeMultipleEquipment(value: number[]){
      this.selectedMultipleEquipmentsIds = value
        if (this.canChooseMultipleEquipments){
          this.adminService.getMultipleEquipmenstByIds(this.selectedMultipleEquipmentsIds).subscribe({
            next: (res) => {
              this.selectedMultipleEquipmentsList = res;
              if (res != null){
                this.canLoadInformationCardAssignment = true;
                this.dialogRef.updateSize('750px', '850px') 
              }
            },
            error: (error) => {
              this.errorMessage = error.error.errors
              this.onError(this.errorMessage)
            }  
        }) 
        }
    }
    
  onChangeMultipleEquipments(event: MatCheckboxChange){
    if (event.checked){
      this.canChooseMultipleEquipments = true
    }
    this.canChooseMultipleEquipments = false
  }

  navigateToEquipments(){
    this.router.navigateByUrl("/equipments")
  }
}
