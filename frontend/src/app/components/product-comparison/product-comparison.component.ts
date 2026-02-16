import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Product, ProductComparisonResponse } from '../../models/models';

@Component({
  selector: 'app-product-comparison',
  templateUrl: './product-comparison.component.html',
  styleUrls: ['./product-comparison.component.css']
})
export class ProductComparisonComponent implements OnInit {

  products: Product[] = [];
  categories: string[] = [];
  productIdA = '';
  productIdB = '';
  comparisonType = 'detailed';
  selectedCategory = '';
  comparisonMode = 'products';

  comparisonResult: ProductComparisonResponse | null = null;
  loading = false;
  loadingProducts = true;
  error = '';

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.categories = [...new Set(products.map(p => p.category))];
        this.loadingProducts = false;
      },
      error: () => {
        this.error = 'Failed to load products.';
        this.loadingProducts = false;
      }
    });
  }

  compareProducts(): void {
    this.loading = true;
    this.error = '';
    this.comparisonResult = null;

    if (this.comparisonMode === 'category') {
      if (!this.selectedCategory) {
        this.error = 'Please select a category.';
        this.loading = false;
        return;
      }
      this.apiService.compareByCategory(this.selectedCategory).subscribe({
        next: (result) => {
          this.comparisonResult = result;
          this.loading = false;
        },
        error: (err) => {
          this.error = err.error?.message || 'Comparison failed.';
          this.loading = false;
        }
      });
    } else {
      if (!this.productIdA || !this.productIdB) {
        this.error = 'Please select both products.';
        this.loading = false;
        return;
      }
      if (this.productIdA === this.productIdB) {
        this.error = 'Please select two different products.';
        this.loading = false;
        return;
      }
      this.apiService.compareProducts({
        productIdA: this.productIdA,
        productIdB: this.productIdB,
        comparisonType: this.comparisonType
      }).subscribe({
        next: (result) => {
          this.comparisonResult = result;
          this.loading = false;
        },
        error: (err) => {
          this.error = err.error?.message || 'Comparison failed.';
          this.loading = false;
        }
      });
    }
  }
}
