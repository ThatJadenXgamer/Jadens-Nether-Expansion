#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 invProjMat;
uniform mat4 invViewMat;
uniform vec3 cameraPos;
uniform float GameTime;

uniform float Intensity;
uniform float Speed;
uniform float Frequency;
uniform float MinDistance;
uniform float MaxDistance;

in vec2 texCoord;
out vec4 fragColor;

float distortionHash(vec2 coordinates) {
    return fract(sin(dot(coordinates, vec2(12.9898, 78.233))) * 43758.5453123);
}

float distortionNoise(vec2 coordinates) {
    vec2 integerPart = floor(coordinates);
    vec2 fractionalPart = fract(coordinates);
    fractionalPart = fractionalPart * fractionalPart * (3.0 - 2.0 * fractionalPart);
    float a = distortionHash(integerPart);
    float b = distortionHash(integerPart + vec2(1.0, 0.0));
    float c = distortionHash(integerPart + vec2(0.0, 1.0));
    float d = distortionHash(integerPart + vec2(1.0, 1.0));
    return mix(mix(a, b, fractionalPart.x), mix(c, d, fractionalPart.x), fractionalPart.y);
}

void main() {
    float rawDepth = texture(MainDepthSampler, texCoord).r;

    float isSky = step(rawDepth, 0.00001) + step(0.99999, rawDepth);
    float depthMask = 1.0 - clamp(isSky, 0.0, 1.0);

    vec3 worldPos = getWorldPos(MainDepthSampler, texCoord, invProjMat, invViewMat, cameraPos);
    float depthFactor = smoothstep(MinDistance, MaxDistance, length(worldPos - cameraPos)) * depthMask;
    float time = GameTime * Speed;

    // X sway calculations //
    float timeX = time * 0.25;
    vec2 noiseScale = vec2(texCoord.x * 1.77, texCoord.y) * (Frequency * 0.05);
    float lateralDistortionPrimary = distortionNoise(vec2(noiseScale.x * 1.5, noiseScale.y * 2.0 - timeX * 1.5));
    float lateralDistortionSecondary = distortionNoise(vec2(noiseScale.x * 0.8 + timeX, noiseScale.y * 1.2 - timeX * 0.5));
    /////////////////////////

    // Y sway calculations //
    vec2 sineScale = vec2(texCoord.x * 1.77, texCoord.y) * Frequency;
    float noisePhaseFirat = distortionNoise(vec2(noiseScale.x * 0.7 + time * 0.2, noiseScale.y * 0.7 - time * 0.3)) * 2.0 - 1.0;
    float noisePhaseSecond = distortionNoise(vec2(noiseScale.x * 0.9 - time * 0.1, noiseScale.y * 0.9 + time * 0.2)) * 2.0 - 1.0;

    float sineSwayY = sin(sineScale.x * 0.5 + time * 1.1 + noisePhaseFirat * 0.8)
    * cos(sineScale.y * 0.7 + time * 0.9 + noisePhaseSecond * 0.8)
    + sin(sineScale.x * 1.8 - time * 1.5 + noisePhaseFirat * 0.5) * 0.4;
    /////////////////////////

    float swayX = (lateralDistortionPrimary + lateralDistortionSecondary) - 1.0;
    float swayY = sineSwayY + (distortionNoise(vec2(noiseScale.x * 1.0 + time * 0.3, noiseScale.y * 1.0 - time * 0.4)) - 0.5) * 0.3;
    vec2 offset = vec2(swayX * 0.6, swayY * 0.9) * Intensity * depthFactor;

    float edgeFade = smoothstep(0.0, 0.05, texCoord.x) * smoothstep(0.0, 0.05, 1.0 - texCoord.x)
    * smoothstep(0.0, 0.05, texCoord.y) * smoothstep(0.0, 0.05, 1.0 - texCoord.y);
    offset *= edgeFade;

    vec2 finalTexCoord = clamp(texCoord + offset, 0.0, 1.0);
    fragColor = texture(DiffuseSampler, finalTexCoord);
}