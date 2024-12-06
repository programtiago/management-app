import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInformationEquipmentUserOwnerComponent } from './modal-information-equipment-user-owner.component';

describe('ModalInformationEquipmentUserOwnerComponent', () => {
  let component: ModalInformationEquipmentUserOwnerComponent;
  let fixture: ComponentFixture<ModalInformationEquipmentUserOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalInformationEquipmentUserOwnerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalInformationEquipmentUserOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
