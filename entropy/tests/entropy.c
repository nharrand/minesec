#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double entropy_step(unsigned char c, double logmax) {
	if(c != 0) {
		return ((double) c) * (log2(c) - logmax);
	} else {
		return 0.0;
	}
}

int main(int argc, char *argv[]) {
	// Check if filename is provided as argument
	if (argc != 2) {
		fprintf(stderr, "Usage: %s <filename>\n", argv[0]);
		return 1;
	}

	// Open the file in binary mode
	FILE *file = fopen(argv[1], "rb");
	if (!file) {
		fprintf(stderr, "Unable to open file '%s'.\n", argv[1]);
		return 1;
	}

	// Determine the length of the file
	fseek(file, 0, SEEK_END);
	size_t fileLen = ftell(file);
	fseek(file, 0, SEEK_SET);

	// Allocate memory for the buffer
	unsigned char *buffer = (unsigned char *)malloc(fileLen);
	if (!buffer) {
		fprintf(stderr, "Memory allocation error.\n");
		fclose(file);
		return 1;
	}

	// Read file contents into buffer
	size_t bytesRead = fread(buffer, 1, fileLen, file);
	fclose(file);

	if (bytesRead != fileLen) {
		fprintf(stderr, "Error reading file '%s'.\n", argv[1]);
		free(buffer);
		return 1;
	}

	// Use buffer as byte array
	// Here you can perform any operations with the byte array

	unsigned char byte_counter[256];
	u_int16_t window_size = 256;
	double entropy = 0.0;
	double logmax = 8.0;
	
	

	if (window_size >= fileLen) {
		fprintf(stderr, "Window size bigger than the file.\n");
		free(buffer);
		return 1;
	}
	
	

	//init char ocunter
	for(u_int16_t i = 0; i < 256; i++) {
		byte_counter[i] = 0;
	}
	for(u_int16_t i = 0; i < window_size; i++) {
		byte_counter[buffer[i]]++;
	}
	//init entropy
	for(u_int16_t i = 0; i < 256; i++) {
		if(byte_counter[i] != 0) {
			entropy -= entropy_step(byte_counter[i], logmax);
		}
	}
	//fprintf(stdout, "Entropy: %f\n", entropy / 256.0);
	
	
	u_int16_t tail = 0;
	u_int16_t head = window_size;
	double max_entropy = entropy;
	
	while(head < (fileLen-1)) {
		//fprintf(stdout, "tail -> '%c' : %i, head -> '%c' : %i\n", buffer[tail], byte_counter[buffer[tail]], buffer[head], byte_counter[buffer[head]]);
		entropy += entropy_step(byte_counter[buffer[tail]], logmax);
		byte_counter[buffer[tail]]--;
		entropy -= entropy_step(byte_counter[buffer[tail]], logmax);
		
		
		entropy += entropy_step(byte_counter[buffer[head]], logmax);
		byte_counter[buffer[head]]++;
		entropy -= entropy_step(byte_counter[buffer[head]], logmax);
		
		tail++;
		head++;
		//fprintf(stdout, "Entropy: %f\n", entropy / 256.0);
		if(entropy > max_entropy) max_entropy = entropy;
	}




	//fprintf(stdout, " -------------------- \n");

	//fprintf(stdout, "Max Entropy: %f\n", max_entropy / 256.0);
	fprintf(stdout, "%f\n", max_entropy / 256.0);



	// Free allocated memory
	free(buffer);

	return 0;
}

