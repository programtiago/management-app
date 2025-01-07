import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalViewUserOwnerEquipmentComponent } from './modal-view-user-owner-equipment.component';

describe('ModalViewUserOwnerEquipmentComponent', () => {
  let component: ModalViewUserOwnerEquipmentComponent;
  let fixture: ComponentFixture<ModalViewUserOwnerEquipmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalViewUserOwnerEquipmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalViewUserOwnerEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
