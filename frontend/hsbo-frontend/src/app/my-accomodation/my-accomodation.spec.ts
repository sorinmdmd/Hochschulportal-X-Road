import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccomodation } from './my-accomodation';

describe('MyAccomodation', () => {
  let component: MyAccomodation;
  let fixture: ComponentFixture<MyAccomodation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyAccomodation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyAccomodation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
