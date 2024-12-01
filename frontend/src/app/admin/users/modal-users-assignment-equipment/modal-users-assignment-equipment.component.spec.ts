import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUsersAssignmentEquipmentComponent } from './modal-users-assignment-equipment.component';

describe('ModalUsersAssignmentEquipmentComponent', () => {
  let component: ModalUsersAssignmentEquipmentComponent;
  let fixture: ComponentFixture<ModalUsersAssignmentEquipmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalUsersAssignmentEquipmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalUsersAssignmentEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
