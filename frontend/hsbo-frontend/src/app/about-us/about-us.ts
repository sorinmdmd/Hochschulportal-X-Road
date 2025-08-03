import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-about-us',
  standalone: false,
  templateUrl: './about-us.html',
  styleUrl: './about-us.css'
})
export class AboutUs {
  response: any;

  constructor(private http: HttpClient) {}

  fetchStudyPrograms() {
    console.log('=== Starting fetchStudyPrograms ===');
    
    console.log('Making HTTP request...');
    this.http.get('http://localhost:8085/api/studyPrograms')
      .subscribe({
        next: (data) => {
          console.log('✅ Success:', data);
          this.response = data;
        },
        error: (error) => {
          console.error('❌ Error fetching study programs:', error);
          console.error('Error status:', error.status);
          console.error('Error message:', error.message);
        },
        complete: () => {
          console.log('🏁 Request completed');
        }
      });
  }
}
