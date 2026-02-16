import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Product, ProductAnalysisResponse } from '../../models/models';

@Component({
  selector: 'app-product-analysis',
  templateUrl: './product-analysis.component.html',
  styleUrls: ['./product-analysis.component.css']
})
export class ProductAnalysisComponent implements OnInit {

  products: Product[] = [];
  selectedProductId = '';
  analysisType = 'detailed';
  analysisResult: ProductAnalysisResponse | null = null;
  loading = false;
  loadingProducts = true;
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.apiService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.loadingProducts = false;
      },
      error: (err) => {
        this.error = 'Failed to load products.';
        this.loadingProducts = false;
      }
    });
  }

  analyzeProduct(): void {
    if (!this.selectedProductId) {
      this.error = 'Please select a product.';
      return;
    }

    this.loading = true;
    this.error = '';
    this.analysisResult = null;

    const observable = this.analysisType === 'brief'
      ? this.apiService.analyzeProductBrief(this.selectedProductId)
      : this.apiService.analyzeProduct(this.selectedProductId);

    observable.subscribe({
      next: (result) => {
        this.analysisResult = result;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'Analysis failed. Please try again.';
        this.loading = false;
      }
    });
  }

  getSelectedProduct(): Product | undefined {
    return this.products.find(p => p.productId === this.selectedProductId);
  }
}
