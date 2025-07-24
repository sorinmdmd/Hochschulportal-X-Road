import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';



interface Accommodation {
  id: string;
  name: string;
  description: string;
  category: string;
  city: string;
  zipCode: string;
  address: string;
  freeSpaces: number;
}


@Component({
  selector: 'app-accomodation',
  standalone: false,
  templateUrl: './accomodation.html',
  styleUrl: './accomodation.css'
})
export class Accomodation implements OnInit {

  accommodations: Accommodation[] = [];
  isLoading = true;
  error?: string;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<Accommodation[]>('http://localhost:8085/api/x-road/accommodations')
      .subscribe({
        next: (data) => {
          this.accommodations = data;
          this.isLoading = false;
        },
        error: (err) => {
          this.error = 'Failed to load accommodations.';
          console.error(err);
          this.isLoading = false;
        }
      });
  }

}
