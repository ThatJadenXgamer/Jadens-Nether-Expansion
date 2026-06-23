#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float TintColorR;
uniform float TintColorG;
uniform float TintColorB;
uniform float DistortionStrength;
uniform float RippleStrength;
uniform float RippleFrequency;
uniform float RippleSpeed;
uniform float RippleRandomness;
uniform float GameTime;

in vec2 texCoord;
out vec4 fragColor;

float random(vec2 vector) {
    return fract(sin(dot(vector.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float randomAngle(float angle) {
    return fract(sin(angle * 12.9898) * 43758.5453);
}

void main() {
    vec2 pixelPos = texCoord * InSize;

    // Frosted Glass distortian
    vec2 gridBase = floor(pixelPos);
    vec2 rand = vec2(random(gridBase + 0.5), random(gridBase + 123.456));
    vec2 randomPixelOffset = (rand - 0.5) * DistortionStrength;

    // Ripple distortain
    vec2 center = vec2(0.5);
    vec2 direction = texCoord - center;
    float distance = length(direction);
    float angle = atan(direction.y, direction.x);

    float angleNoise = randomAngle(angle * 8.0) * 2.0 - 1.0;
    float angularPerturb = angleNoise * RippleRandomness;
    float effectiveDist = distance + angularPerturb * 0.05;

    float phase1 = effectiveDist * RippleFrequency - GameTime * RippleSpeed;
    float wave1 = sin(phase1);

    float phase2 = effectiveDist * (RippleFrequency * 2.5) - GameTime * (RippleSpeed * 1.3);
    float wave2 = sin(phase2) * 0.4;

    float combinedWave = (wave1 + wave2) * 0.7;
    vec2 ripplePixelOffset = (distance > 0.0 ? normalize(direction) : vec2(0.0)) * combinedWave * RippleStrength;

    // take the frosted glass and the ripple and smash the two shaders together to create imaginary shader
    vec2 totalPixelOffset = randomPixelOffset + ripplePixelOffset;
    vec2 invInSize = 1.0 / InSize;
    vec2 distortedCoord = texCoord + totalPixelOffset * invInSize;
    vec3 distortedColor = texture(DiffuseSampler, distortedCoord).rgb;
    vec3 finalColor = distortedColor * vec3(TintColorR, TintColorG, TintColorB);

    fragColor = vec4(finalColor, 1.0);
}