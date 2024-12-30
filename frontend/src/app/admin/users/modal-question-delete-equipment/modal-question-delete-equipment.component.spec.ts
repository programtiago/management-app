import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalQuestionDeleteEquipmentComponent } from './modal-question-delete-equipment.component';

describe('ModalQuestionDeleteEquipmentComponent', () => {
  let component: ModalQuestionDeleteEquipmentComponent;
  let fixture: ComponentFixture<ModalQuestionDeleteEquipmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalQuestionDeleteEquipmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalQuestionDeleteEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
