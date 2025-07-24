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
  postStatusMessage?: string;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAccommodations();
  }

  loadAccommodations(): void {
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

  startAccommodation(accommodationId: string): void {
    // Current date/time in Instant format (ISO 8601 with Zulu time)
    const startDate = new Date().toISOString();

    const url = `http://localhost:8085/api/x-road/accommodation/${accommodationId}/${startDate}`;

    this.http.post(url, {}).subscribe({
      next: () => {
        this.postStatusMessage = `Started accommodation with ID ${accommodationId} successfully.`;
        // Optionally refresh the list or update UI as needed
      },
      error: (err) => {
        this.postStatusMessage = `Failed to start accommodation with ID ${accommodationId}.`;
        console.error(err);
      }
    });
  }

}
