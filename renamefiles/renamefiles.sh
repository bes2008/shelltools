#!/bin/bash

function func_usage() {
  echo "usage: renamefiles.sh <source_dir> <file_pattern> <sequence_pattern> <prefix> <sequence_length> <suffix_pattern>"
  echo "args:"
  echo "  source_dir: the directory. required"
  echo "  file_pattern: the regex of source files. required"
  echo "  sequence_pattern: the sequenc pattern. requried"
  echo "  prefix: the prefix of the new filename. required"
  echo "  sequence_length: the length of sequence in the new filename. requried"
  echo "  suffix_pattern: the pattern of suffix in the new filename. optional"
}

if [[ $# < 3 ]]; then
  func_usage
  exit 1
fi

source_dir=$1
file_pattern=$2
sequence_pattern=$3
prefix=$4
sequence_length=$5
suffix_pattern=$6

if [[ ! -d "$source_dir" ]]; then
  echo "source_dir not exists: $source_dir"	
fi

 
# ls $source_dir | grep -E "$file_pattern"

find "$source_dir" -maxdepth 1 -name "$file_pattern" | while read -r filepath; do
  
  filename=$(basename "$filepath")
  file_extention="${filename##*.}"
  filename_without_ext="${filename%.$file_extention}"
  echo "========================================="
  echo "filepath: $filepath , filename: $filename , file_extention: $file_extention, filename_without_ext: $filename_without_ext"

  # compute new file sequence
  file_sequence=$(echo "$filename_without_ext" | grep -E -o "$sequence_pattern" | grep -E -o "[0-9]+")
  echo "file_sequence: $file_sequence"

  file_suffix=""
  if [[ -n "$suffix_pattern" ]]; then
     file_suffix=$(echo "$filename_without_ext" | grep -E -o "$suffix_pattern")	  
  fi	  
  echo "file_suffix: $file_suffix"
  
  fixedLengthSequence="$file_sequence"
  while [[ ${#fixedLengthSequence} < $sequence_length ]]; do
    fixedLengthSequence="0$fixedLengthSequence"	  
  done
  echo "fixedLengthSequence: $fixedLengthSequence"
  

  directory=$(dirname "$filepath")
  newfilename="${prefix}${fixedLengthSequence}${file_suffix}.${file_extention}"
  newfilepath="${directory}/${newfilename}"
  echo "new filename: ${newfilename}"
  echo "new filepath: ${newfilepath}"
  

  mv "$filepath" "$newfilepath"
done

