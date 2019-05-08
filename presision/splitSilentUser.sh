#!/bin/sh
cd /data/dmp/layer/12
file1=`date +'%Y%m%d'`
file2='silent_history'
grep -v -f ${file2} ${file1} > tmp
head -n 10 tmp > tmp1
cp -rf tmp1 ${file1}
cat tmp1 >> ${file2}
rm tmp
rm tmp1