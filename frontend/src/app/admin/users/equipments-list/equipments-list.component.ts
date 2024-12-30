import { Component, Input, OnInit } from '@angular/core';
import { Equipment } from '../../../model/equiment';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserEquipment } from '../../../model/user-equipment';
import { config } from 'rxjs';
import { ModalQuestionDeleteEquipmentComponent } from '../modal-question-delete-equipment/modal-question-delete-equipment.component';

@Component({
  selector: 'app-equipments-list',
  templateUrl: './equipments-list.component.html',
  styleUrl: './equipments-list.component.scss'
})
export class EquipmentsListComponent implements OnInit{
  
  @Input() equipments: Equipment[] = [];
  userEquipmentsOfEquipmentSelected: UserEquipment[] = []

  statusEquipment: string = ""

  displayedColumns: String[] = ['brand', 'model', 'description', 'serialNumber', 'registryDate', 'status', 'actions']

  constructor(private adminService: AdminService, private snackbar: MatSnackBar, private dialog: MatDialog){}

  ngOnInit(): void {}

  onRefresh(){
    this.adminService.getEquipments().subscribe((res) => {
      this.equipments = res;
    })
  }

  onDelete(equipmentId: number){
    this.adminService.getEquipmentById(equipmentId).subscribe((res) => {
      this.userEquipmentsOfEquipmentSelected = res.usersEquipments;
      if (this.userEquipmentsOfEquipmentSelected.length === 0){
          this.adminService.deleteEquipmentById(equipmentId);
      }else if (this.userEquipmentsOfEquipmentSelected.length > 0){
            const dialogRef = this.dialog.open(ModalQuestionDeleteEquipmentComponent, {
              height: '220px',
              width: '400px',
              data: [`It's not possible to delete this equipment because this resource is assigned to a user ! First remove the user from the equipment, and try again.`]
            })
          }
    })
  }   
}
