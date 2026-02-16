import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { DashboardSummary, Product } from '../../models/models';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  summary: DashboardSummary | null = null;
  loading = true;
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    this.error = '';
    this.apiService.getDashboardSummary().subscribe({
      next: (data) => {
        this.summary = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load dashboard data. Please ensure the backend is running.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  getCategoryIcon(category: string): string {
    const icons: { [key: string]: string } = {
      'Vital Signs Monitoring': 'bi-heart-pulse',
      'Glucose Monitoring': 'bi-droplet',
      'Cardiac Monitoring': 'bi-activity',
      'Sleep Monitoring': 'bi-moon-stars',
      'Neurological Monitoring': 'bi-cpu'
    };
    return icons[category] || 'bi-box';
  }

  getCategoryColor(category: string): string {
    const colors: { [key: string]: string } = {
      'Vital Signs Monitoring': '#e74c3c',
      'Glucose Monitoring': '#3498db',
      'Cardiac Monitoring': '#e91e63',
      'Sleep Monitoring': '#9b59b6',
      'Neurological Monitoring': '#00bcd4'
    };
    return colors[category] || '#95a5a6';
  }
}
