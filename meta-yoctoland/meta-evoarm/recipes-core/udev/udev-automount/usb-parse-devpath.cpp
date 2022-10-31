#include <iostream>
#include <regex>
#include <string>

int main(int argc, char* argv[])
{
    if (argc < 2)
    {
        std::cout << "error" << std::endl;
        return 1;
    }

    std::string input(argv[1]);
    std::regex usb_regex(".*/(usb[0-9]+)/.*");
    std::smatch result;
    std::regex_match(input, result, usb_regex);

    if(result.size() > 1){
        std::ssub_match base_sub_match = result[1];
        std::string base = base_sub_match.str();
        std::cout << base << '\n';
    }
    else
        std::cout << "unkown" << std::endl;
}
