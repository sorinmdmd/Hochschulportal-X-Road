import { Component , OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';


interface StudyProgram {
  id: string;
  name: string;
  degreeLevel: string;
  numberOfSemesters: number;
  facultyName: string;
  campusName: string;
}

interface PersonalStudentInfo {
  id: string;
  nationality: string;
  dateOfBirth: string;
  studentId: number;
  startDate: string;
  actualSemester: number;
  studyProgram: StudyProgram;
}

@Component({
  selector: 'app-my-profile',
  standalone: false,
  templateUrl: './my-profile.html',
  styleUrl: './my-profile.css'
})
export class MyProfile implements OnInit {
  personalInfo?: PersonalStudentInfo;
  isLoading = true;
  error?: string;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<PersonalStudentInfo>('http://localhost:8085/api/personalStudentInfo')
      .subscribe({
        next: (data) => {
          this.personalInfo = data;
          this.isLoading = false;
        },
        error: (err) => {
          this.error = 'Failed to load profile information.';
          console.error(err);
          this.isLoading = false;
        }
      });
  }
}
