import { Component, Input, OnInit } from '@angular/core';
import { Equipment } from '../../../model/equipment/equiment';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserEquipment } from '../../../model/user-equipment/user-equipment';
import { ModalQuestionDeleteEquipmentComponent } from '../modal-question-delete-equipment/modal-question-delete-equipment.component';
import { ModalViewUserOwnerEquipmentComponent } from '../modal-view-user-owner-equipment/modal-view-user-owner-equipment.component';
import { User } from '../../../model/user/user';
import { StatusEquipment } from '../../../model/equipment/status-equipment';

@Component({
  selector: 'app-equipments-list',
  templateUrl: './equipments-list.component.html',
  styleUrl: './equipments-list.component.scss'
})
export class EquipmentsListComponent implements OnInit{
  
  @Input() equipments: Equipment[] = [];
  userEquipmentsOfEquipmentSelected: UserEquipment[] = []
  userOwnerEquipment!: User;
  
  statusEquipment = StatusEquipment;

  displayedColumns: String[] = ['brand', 'model', 'description', 'serialNumber', 'registryDate', 'status', 'actions']

  constructor(private adminService: AdminService, private snackbar: MatSnackBar, private dialog: MatDialog){}

  ngOnInit(): void {}

  onRefresh(){
    this.adminService.getEquipments().subscribe((res) => {
      this.equipments = res.content;
    })
  }

  onDelete(equipmentId: number){
    this.adminService.getEquipmentById(equipmentId).subscribe((res) => {
      this.userEquipmentsOfEquipmentSelected = res.usersEquipments;
      var userEquipmentsEmpty : boolean = this.userEquipmentsOfEquipmentSelected.length === 0;

      if (userEquipmentsEmpty){
        this.adminService.deactivateEquipmentById(equipmentId).subscribe(
          () => {
            this.snackbar.open('Equipment deleted successfully', 'Close', 
              { duration: 3000, verticalPosition: 'top', horizontalPosition: 'right' });
              this.onRefresh();
          });
      }else if (!userEquipmentsEmpty){
          const dialogRef = this.dialog.open(ModalQuestionDeleteEquipmentComponent, {
            height: '220px',
            width: '400px',
            data: [`It's not possible to delete this equipment because this resource is assigned to a user ! First remove the user from the equipment, and try again.`]
          })
        }
    })   
  }   

  openModalViewUserOwner(equipmentId: number){
    this.adminService.getEquipmentById(equipmentId).subscribe((res) => {
        this.userEquipmentsOfEquipmentSelected = res.usersEquipments;

        for (var i = 0; i < this.userEquipmentsOfEquipmentSelected.length; i++){
          this.userOwnerEquipment = this.userEquipmentsOfEquipmentSelected[i].user
        }
        
        const dialogRef = this.dialog.open(ModalViewUserOwnerEquipmentComponent, {
        height: '280px',
        width: '400px',
        data: this.userOwnerEquipment
    })
    })  
  }

  openModalToAssignEquipmentToUser(){
    console.log('clicou')
  }
}
