auto a[10];
auto i , j;
putchar('Input the grades of ten students in sequence');
putchar('length for 10 ');
i = 0;
A:
while(i < 10){
   getnumb(j);
   a[i] = j;
   i++;
}
putchar('Your array like this');
putchar('use putstr to output array');
putstr(a); /* Êä³öÊı×é */
putchar('use for function to output array');
auto m;
for(m = 0; m < 10; m++) {
   putnumb(a[m]);
}
putchar('Now we find the largest number you have entered ');
auto k = 0;
auto max;
auto temp, sum;
max = a[0];
sum = 0;
while( k < 10 ){
    temp = a[k];
    if(temp > max) {
         max = temp;
    }
    sum = sum + temp;
    k++;
}
sum = sum / 10;
putnumb(max);
putchar('the average grade of ten students is ');
putnumb(sum);

    
