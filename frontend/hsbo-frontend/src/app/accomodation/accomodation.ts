import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';



interface HousingUnit {
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

  accommodations: HousingUnit[] = [];
  isLoading = true;
  error?: string;
  postStatusMessage?: string;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadHousingUnits();
  }

  loadHousingUnits(): void {
    this.http.get<HousingUnit[]>('http://localhost:8085/api/x-road/housingUnits')
      .subscribe({
        next: (data) => {
          this.accommodations = data;
          this.isLoading = false;
        },
        error: (err) => {
          this.error = 'Failed to load HousingUnits.';
          console.error(err);
          this.isLoading = false;
        }
      });
  }

  startHousingUnitBooking(accommodationId: string): void {
    // Current date/time in Instant format (ISO 8601 with Zulu time)
    const startDate = new Date().toISOString();

    const url = `http://localhost:8085/api/x-road/housingUnit/${accommodationId}/${startDate}`;

    this.http.post(url, {}).subscribe({
      next: () => {
        this.postStatusMessage = `Started accommodation with ID ${accommodationId} successfully.`;
        this.loadHousingUnits()
        // Optionally refresh the list or update UI as needed
      },
      error: (err) => {
        this.postStatusMessage = `Failed to start accommodation with ID ${accommodationId}.`;
        console.error(err);
      }
    });
  }

}
