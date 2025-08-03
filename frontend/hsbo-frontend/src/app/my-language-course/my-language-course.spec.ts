import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyLanguageCourse } from './my-language-course';

describe('MyLanguageCourse', () => {
  let component: MyLanguageCourse;
  let fixture: ComponentFixture<MyLanguageCourse>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyLanguageCourse]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyLanguageCourse);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
