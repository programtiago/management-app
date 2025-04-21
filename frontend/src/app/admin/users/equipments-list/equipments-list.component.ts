import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Equipment } from '../../../model/equipment/equiment';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserEquipment } from '../../../model/user-equipment/user-equipment';
import { ModalQuestionDeleteEquipmentComponent } from '../modal-question-delete-equipment/modal-question-delete-equipment.component';
import { ModalViewUserOwnerEquipmentComponent } from '../modal-view-user-owner-equipment/modal-view-user-owner-equipment.component';
import { User } from '../../../model/user/user';
import { StatusEquipment } from '../../../model/equipment/status-equipment';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-equipments-list',
  templateUrl: './equipments-list.component.html',
  styleUrl: './equipments-list.component.scss'
})
export class EquipmentsListComponent implements OnInit, AfterViewInit{
  
  @Input() equipments: Equipment[] = [];
  @Output() filteredEquipments = new EventEmitter<{equipments: Equipment[]; totalElements: number}>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  searchKeyword: string = '';
  dataSource = new MatTableDataSource<Equipment>(this.equipments);
  searchResult: undefined | Equipment[];
  
  userEquipmentsOfEquipmentSelected: UserEquipment[] = []
  userOwnerEquipment!: User;

  totalElements = 0;
  
  statusEquipment = StatusEquipment;

  displayedColumns: String[] = ['brand', 'model', 'description', 'serialNumber', 'registryDate', 'status', 'actions']

  constructor(private adminService: AdminService, private snackbar: MatSnackBar, private dialog: MatDialog){}

  ngOnInit(): void {
  }
  
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

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

  filterByAll(){
    this.adminService.getEquipments().subscribe((res) => {
      this.equipments = res.content
      this.dataSource.data = this.equipments;
      this.filteredEquipments.emit({ equipments: this.equipments, totalElements: res.totalElements})
    })
  }


  applyFilter(event: Event){
    const filterValue = (event.target as HTMLInputElement).value;
    this.searchKeyword = filterValue.trim();

    const pageSize = this.paginator ? this.paginator.pageSize : 10
    this.searchEquipmentsByKeyword(0, pageSize)
  }

  searchEquipmentsByKeyword(pageIndex: number = 0, pageSize: number = 10){
    if (this.searchKeyword.trim() !== ''){
      this.adminService.searchEquipments(this.searchKeyword, pageIndex, pageSize)
      .subscribe((res) => {
        this.equipments = res.content;
        this.dataSource.data = this.equipments;
        this.filteredEquipments.emit({equipments: this.equipments, totalElements: res.totalElements})
        if (this.paginator){
          this.paginator.length = res.totalElements;
          this.paginator.pageIndex = pageIndex;
        }
      })
    }else{
      this.filterByAll();
    }
  }
}
