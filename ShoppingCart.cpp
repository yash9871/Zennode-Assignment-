/******************************************************************************

                              Online C++ Compiler.
               Code, Compile, Run and Debug C++ program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <iostream>
#include<bits/stdc++.h>
using namespace std;

float calculateDiscount(float amount,float discount) {
    float disc_amt = (amount*discount)/100;
    return disc_amt;
}

int main()
{
    
    string prod_A = "Product A";
    string prod_B = "Product B";
    string prod_C = "Product C";
    
    float price_A = 20 , price_B = 40 , price_C = 50 , shippingFee = 0,giftFee = 0;
    
    int quant_A,quant_B,quant_C,total_quant,gift;
    
    cout << "Enter quantity of " << prod_A << endl;
    cin >> quant_A;
    cout << "Enter quantity of " << prod_B << endl;
    cin >> quant_B;
    cout << "Enter quantity of " << prod_C << endl;
    cin >> quant_C;
    
    cout << "Enter 1 for Gift Wrap or 0 for normal delivery!" << endl;
    cin >> gift;
    bool isGiftWrap = gift == 1 ? true:false;
    
    
    total_quant = quant_A + quant_B + quant_C;
    float total_amount = (price_A*quant_A)+(price_B*quant_B)+(price_C*quant_C);
    
    giftFee = isGiftWrap == true ? total_quant : 0;
    shippingFee = total_quant%10 == 0 ? (total_quant/10)*5 : ((total_quant/5)+1)*5;
    
    float flat_10 = 0 , bulk_5 = 0, bulk_10 = 0, tiered_50 = 0;
    
    priority_queue<pair<string,float>> pq;
    
    // calculating "flat_10_discount"
    if(total_amount > 200) {
        flat_10 = calculateDiscount(total_amount,20);
        pq.push({"flat_10_discount",flat_10});
    }
    
    // calculating "bulk_5_discount"
    if(quant_A > 10 || quant_B > 10 || quant_C > 10) {
        if(quant_A > 10) {
            bulk_5 = calculateDiscount(quant_A*price_A,5);
        }
        else if(quant_B > 10) {
            bulk_5 = calculateDiscount(quant_B*price_B,5);
        }
        else {
            bulk_5 = calculateDiscount(quant_C*price_C,5);
        }
        pq.push({"bulk_5_discount",bulk_5});
    }
    
    // calculating "bulk_10_discount"
    if(total_quant > 20) {
        bulk_10 = calculateDiscount(total_amount,10);
        pq.push({"bulk_10_discount",bulk_10});
    }
    
    // calculating "tiered_50_discount"
    if(total_quant > 30 && (quant_C > 15 || quant_B > 15 || quant_A > 15)) {
        if(quant_A > 15) {
            int amt = (price_A*quant_A)-(price_A*(quant_A-15));
            tiered_50 = calculateDiscount(amt,50);
        }
        else if(quant_B > 15) {
            int amt = (price_B*quant_B)-(price_B*(quant_B-15));
            tiered_50 = calculateDiscount(amt,50);
        }
        else {
            int amt = (price_C*quant_C)-(price_C*(quant_C-15));
            tiered_50 = calculateDiscount(amt,50);
        }
        pq.push({"tiered_50_discount",tiered_50});
    }
    
    
    float finalAmount = total_amount-pq.top().second + giftFee + shippingFee;
    
    cout << prod_A << "            " << quant_A << "           $" << (price_A*quant_A) << endl; 
    cout << prod_B << "            " << quant_B << "           $" << (price_B*quant_B) << endl;
    cout << prod_C << "            " << quant_C << "           $" << (price_C*quant_C) << endl;
    
    cout << "SubTotal : $" << total_amount << endl;
    cout << "Discount Applied : " << pq.top().first << endl;
    cout << "Discount amount : $" << pq.top().second << endl;
    cout << "Shipping Fees : $" << shippingFee << endl;
    cout << "Gift Fee : $" << giftFee << endl;
    
    cout << "Total Amount : $" << finalAmount << endl;
    
    return 0;
}
