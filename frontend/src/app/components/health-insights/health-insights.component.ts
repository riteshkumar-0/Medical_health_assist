import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Product, HealthInsightsResponse, WellnessRecommendationResponse } from '../../models/models';

@Component({
  selector: 'app-health-insights',
  templateUrl: './health-insights.component.html',
  styleUrls: ['./health-insights.component.css']
})
export class HealthInsightsComponent implements OnInit {

  products: Product[] = [];
  selectedProductId = '';

  insightsResult: HealthInsightsResponse | null = null;
  wellnessResult: WellnessRecommendationResponse | null = null;

  loadingInsights = false;
  loadingWellness = false;
  loadingProducts = true;
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.loadingProducts = false;
      },
      error: () => {
        this.error = 'Failed to load products.';
        this.loadingProducts = false;
      }
    });
  }

  generateInsights(): void {
    this.loadingInsights = true;
    this.error = '';
    this.insightsResult = null;

    this.apiService.getHealthInsights().subscribe({
      next: (result) => {
        this.insightsResult = result;
        this.loadingInsights = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'Failed to generate insights.';
        this.loadingInsights = false;
      }
    });
  }

  getWellnessRecommendations(): void {
    if (!this.selectedProductId) {
      this.error = 'Please select a product.';
      return;
    }

    this.loadingWellness = true;
    this.error = '';
    this.wellnessResult = null;

    this.apiService.getWellnessRecommendations(this.selectedProductId).subscribe({
      next: (result) => {
        this.wellnessResult = result;
        this.loadingWellness = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'Failed to get recommendations.';
        this.loadingWellness = false;
      }
    });
  }
}
